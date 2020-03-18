app.service("uploadService", function ($http) {
    this.parseFile = function () {
        var formData = new FormData();
        formData.append('file', file.files[0]);
        console.log(file.files[0]);
        return $http({
            method: 'POST',
            url: basePath + "/upload/parseWord",
            data: formData,
            headers: {'Content-Type': undefined},//angularjs自动十倍类型为multipart/formdata类型
            transformRequest: angular.identity
        });
    }
    this.uploadPhoto=function(){
        var formData = new FormData();

        formData.append('file',file.files[0])

        return $http({
            method:'POST',
            url: basePath + "/upload/photo",
            data:formData,
            headers:{'Content-Type':undefined},//angularjs自动十倍类型为multipart/formdata类型
            transformRequest:angular.identity
        });
    }
});