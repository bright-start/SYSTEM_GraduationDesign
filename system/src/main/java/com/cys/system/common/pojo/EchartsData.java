package com.cys.system.common.pojo;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EchartsData {
    private Title title;
    private ToolTip tooltip;
    private Legend legend;
    private ToolBOX toolbox;
    private Grid grid;
    private List<XAxis> xAxis;
    private List<YAxis> yAxis;
    private List<Series> series;

    public class Title {
        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    public class ToolTip {
        private String trigger;
        private String formatter;
        private AxisPointer axisPointer;


        public class AxisPointer {
            private String type;
            private Label label;

            public class Label {
                private String backgroundColor;

                public String getBackgroundColor() {
                    return backgroundColor;
                }

                public void setBackgroundColor(String backgroundColor) {
                    this.backgroundColor = backgroundColor;
                }
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public Label getLabel() {
                return label;
            }

            public void setLabel(Label label) {
                this.label = label;
            }
        }

        public String getTrigger() {
            return trigger;
        }

        public void setTrigger(String trigger) {
            this.trigger = trigger;
        }

        public String getFormatter() {
            return formatter;
        }

        public void setFormatter(String formatter) {
            this.formatter = formatter;
        }

        public AxisPointer getAxisPointer() {
            return axisPointer;
        }

        public void setAxisPointer(AxisPointer axisPointer) {
            this.axisPointer = axisPointer;
        }
    }

    public class Legend {
        private String orient;
        private String left;
        private String[] data;

        public String getOrient() {
            return orient;
        }

        public void setOrient(String orient) {
            this.orient = orient;
        }

        public String getLeft() {
            return left;
        }

        public void setLeft(String left) {
            this.left = left;
        }

        public String[] getData() {
            return data;
        }

        public void setData(String[] data) {
            this.data = data;
        }
    }

    public class ToolBOX {
        private Feature feature;

        public Feature getFeature() {
            return feature;
        }

        public void setFeature(Feature feature) {
            this.feature = feature;
        }

        public class Feature {
            private SaveAsImage saveAsImage = new SaveAsImage();

            public SaveAsImage getSaveAsImage() {
                return saveAsImage;
            }

            public void setSaveAsImage(SaveAsImage saveAsImage) {
                this.saveAsImage = saveAsImage;
            }

            public class SaveAsImage {
            }
        }
    }

    public class Grid {
        private String left;
        private String right;
        private String bottom;
        private boolean containLabel;

        public String getLeft() {
            return left;
        }

        public void setLeft(String left) {
            this.left = left;
        }

        public String getRight() {
            return right;
        }

        public void setRight(String right) {
            this.right = right;
        }

        public String getBottom() {
            return bottom;
        }

        public void setBottom(String bottom) {
            this.bottom = bottom;
        }

        public boolean isContainLabel() {
            return containLabel;
        }

        public void setContainLabel(boolean containLabel) {
            this.containLabel = containLabel;
        }
    }

    public class XAxis {
        private String type;
        private boolean boundaryGap;
        private List<String> data;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public boolean isBoundaryGap() {
            return boundaryGap;
        }

        public void setBoundaryGap(boolean boundaryGap) {
            this.boundaryGap = boundaryGap;
        }

        public List<String> getData() {
            return data;
        }

        public void setData(List<String> data) {
            this.data = data;
        }
    }

    public class YAxis {
        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public class Series implements Cloneable {
        private String name;
        private String type;
        private String[] radius;
        private boolean avoidLabelOverlap;
        private Label label;
        private Emphasis emphasis;
        private String stack;
        private AreaStyle areaStyle;
        private LabelLine labelLine;
        private List<Object> data;

        @Override
        public Series clone() throws CloneNotSupportedException {
            Series newSeries = (Series) super.clone();
            return newSeries;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStack() {
            return stack;
        }

        public void setStack(String stack) {
            this.stack = stack;
        }

        public AreaStyle getAreaStyle() {
            return areaStyle;
        }

        public void setAreaStyle(AreaStyle areaStyle) {
            this.areaStyle = areaStyle;
        }

        public String[] getRadius() {
            return radius;
        }

        public void setRadius(String[] radius) {
            this.radius = radius;
        }

        public boolean isAvoidLabelOverlap() {
            return avoidLabelOverlap;
        }

        public void setAvoidLabelOverlap(boolean avoidLabelOverlap) {
            this.avoidLabelOverlap = avoidLabelOverlap;
        }

        public Label getLabel() {
            return label;
        }

        public void setLabel(Label label) {
            this.label = label;
        }

        public Emphasis getEmphasis() {
            return emphasis;
        }

        public void setEmphasis(Emphasis emphasis) {
            this.emphasis = emphasis;
        }

        public LabelLine getLabelLine() {
            return labelLine;
        }

        public void setLabelLine(LabelLine labelLine) {
            this.labelLine = labelLine;
        }

        public List<Object> getData() {
            return data;
        }

        public void setData(List<Object> data) {
            this.data = data;
        }

        public class AreaStyle {
        }


        public class Label {
            private boolean show;
            private String position;
            private String fontSize;
            private String fontWeight;

            public boolean isShow() {
                return show;
            }

            public void setShow(boolean show) {
                this.show = show;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public String getFontSize() {
                return fontSize;
            }

            public void setFontSize(String fontSize) {
                this.fontSize = fontSize;
            }

            public String getFontWeight() {
                return fontWeight;
            }

            public void setFontWeight(String fontWeight) {
                this.fontWeight = fontWeight;
            }
        }

        public class Emphasis {
            private Series.Label label;

            public Label getLabel() {
                return label;
            }

            public void setLabel(Label label) {
                this.label = label;
            }
        }

        public class LabelLine {
            private boolean show;

            public boolean isShow() {
                return show;
            }

            public void setShow(boolean show) {
                this.show = show;
            }
        }

    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public ToolTip getTooltip() {
        return tooltip;
    }

    public void setTooltip(ToolTip tooltip) {
        this.tooltip = tooltip;
    }

    public Legend getLegend() {
        return legend;
    }

    public void setLegend(Legend legend) {
        this.legend = legend;
    }

    public ToolBOX getToolbox() {
        return toolbox;
    }

    public void setToolbox(ToolBOX toolbox) {
        this.toolbox = toolbox;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public List<XAxis> getxAxis() {
        return xAxis;
    }

    public void setxAxis(List<XAxis> xAxis) {
        this.xAxis = xAxis;
    }

    public List<YAxis> getyAxis() {
        return yAxis;
    }

    public void setyAxis(List<YAxis> yAxis) {
        this.yAxis = yAxis;
    }

    public List<Series> getSeries() {
        return series;
    }

    public void setSeries(List<Series> series) {
        this.series = series;
    }
}
