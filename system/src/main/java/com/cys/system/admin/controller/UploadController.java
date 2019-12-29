package com.cys.system.admin.controller;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.exception.ServerErrorException;
import com.cys.system.common.util.FileUtil;
import com.google.gson.internal.$Gson$Preconditions;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.*;
import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.UUID;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/upload")
public class UploadController {

    private final static Logger logger = LoggerFactory.getLogger(UploadController.class);

    @PostMapping("/parseWord")
    public Result parseWord(MultipartFile file, HttpServletRequest request) throws ServerErrorException {
        //临时文件
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String realPath = request.getSession().getServletContext().getRealPath(File.separator + "temp/article");
        File realFile = new File(realPath);
        if (!realFile.exists()) {
            realFile.mkdirs();
        }
        String tempFile = realPath + File.separator + file.getOriginalFilename();
        String htmlPath = null;
        try {
            file.transferTo(new File(tempFile));
            String wordName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf("."));
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            switch (suffix) {
                case ".doc":
                    htmlPath = word2003ToHtml(realPath, wordName, suffix, realPath);
                    break;
                case ".docx":
                    htmlPath = word2007ToHtml(realPath, wordName, suffix, realPath);
                    break;
                default:
                    return new Result().success(200, "格式不支持");
            }
        } catch (Exception e) {
            logger.error(String.valueOf(e.getMessage()));
            return new Result().success(200, "上传失败");
        } finally {
            new File(tempFile).delete();
        }
        try {
            byte[] bytesByFile = FileUtil.getBytesByFile(new File(htmlPath));
            String content = new String(bytesByFile);
//            过滤<script>标签 预防潜在的威胁
//            content = content.replaceAll("<script.*?>(.*?)</script>","");
//            content = content.replaceAll("&", "&amp;");
//            content = content.replaceAll("<", "&lt;");
//            content = content.replaceAll(">", "&gt;");
//            content = content.replaceAll("\"", "&quot;");

            return new Result().success(content);
        } catch (Exception e) {
            throw new ServerErrorException();
        }

    }

    /**
     * 将word2003转换为html文件
     *
     * @param wordPath word文件路径
     * @param wordName word文件名称无后缀
     * @param suffix   word文件后缀
     * @param htmlPath html存储地址
     * @throws IOException
     * @throws TransformerException
     * @throws ParserConfigurationException
     */
    public static String word2003ToHtml(String wordPath, String wordName, String suffix, String htmlPath)
            throws IOException, TransformerException, ParserConfigurationException {
        String htmlName = wordName + ".html";
        final String imagePath = htmlPath + "images" + File.separator;
        // 判断html文件是否存在
        File htmlFile = new File(htmlPath + htmlName);
        if (htmlFile.exists()) {
            return htmlFile.getAbsolutePath();
        }
        // 原word文档
        final String file = wordPath + File.separator + wordName + suffix;
        InputStream input = new FileInputStream(new File(file));
        HWPFDocument wordDocument = new HWPFDocument(input);
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
                DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
        // 设置图片存放的位置
        wordToHtmlConverter.setPicturesManager(new PicturesManager() {
            public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches,
                                      float heightInches) {
                File imgPath = new File(imagePath);
                if (!imgPath.exists()) {// 图片目录不存在则创建
                    imgPath.mkdirs();
                }
                File file = new File(imagePath + suggestedName);
                try {
                    OutputStream os = new FileOutputStream(file);
                    os.write(content);
                    os.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // 图片在html文件上的路径 相对路径
                return wordPath + "/images/" + suggestedName;
            }
        });
        // 解析word文档
        wordToHtmlConverter.processDocument(wordDocument);
        Document htmlDocument = wordToHtmlConverter.getDocument();
        // 生成html文件上级文件夹
        File folder = new File(htmlPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        OutputStream outStream = new FileOutputStream(htmlFile);
        DOMSource domSource = new DOMSource(htmlDocument);
        StreamResult streamResult = new StreamResult(outStream);
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer serializer = factory.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        serializer.transform(domSource, streamResult);
        return htmlFile.getAbsolutePath();
    }

    /**
     * 2007版本word转换成html
     *
     * @param wordPath word文件路径
     * @param wordName word文件名称无后缀
     * @param suffix   word文件后缀
     * @param htmlPath html存储地址
     * @return
     * @throws IOException
     */
    public static String word2007ToHtml(String wordPath, String wordName, String suffix, String htmlPath)
            throws IOException {
        String htmlName = wordName + ".html";
        String imagePath = htmlPath + File.separator + "images" + File.separator;
        // 判断html文件是否存在
        File htmlFile = new File(htmlPath + File.separator + htmlName);
        if (htmlFile.exists()) {
            return htmlFile.getAbsolutePath();
        }
        // word文件
        File wordFile = new File(wordPath + File.separator + wordName + suffix);
        // 1) 加载word文档生成 XWPFDocument对象
        InputStream in = new FileInputStream(wordFile);
        XWPFDocument document = new XWPFDocument(in);
        // 2) 解析 XHTML配置 (这里设置IURIResolver来设置图片存放的目录)
        File imgFolder = new File(imagePath);
        XHTMLOptions options = XHTMLOptions.create();
        options.setExtractor(new FileImageExtractor(imgFolder));
        // html中图片的路径 相对路径
        options.URIResolver(new BasicURIResolver(wordPath + "/images"));
        options.setIgnoreStylesIfUnused(false);
        options.setFragment(true);
        // 3) 将 XWPFDocument转换成XHTML
        // 生成html文件上级文件夹
        File folder = new File(htmlPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        OutputStream out = new FileOutputStream(htmlFile);
        XHTMLConverter.getInstance().convert(document, out, options);
        return htmlFile.getAbsolutePath();
    }
}
