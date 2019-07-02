$.views.settings.allowCode(true);
$.views.converters("getResponseModelName", function (val) {
    return getResponseModelName(val);
});

var tempBody = $.templates('#temp_body');
var tempBodyResponseModel = $.templates('#temp_body_response_model');
var tempBodyRefModel = $.templates('#temp_body_ref_model');

//获取context path
var contextPath = getContextPath();

function getContextPath() {
    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var result = pathName.substr(0, index + 1);
    return result;
}


$(function () {
    $.ajax({
        url: "v2/api-docs",
// 	        url : "http://petstore.swagger.io/v2/swagger.json",
        dataType: "json",
        type: "get",
        async: false,
        success: function (data) {
            //layui init
            layui.use(['layer', 'jquery', 'element'], function () {
                var $ = layui.jquery, layer = layui.layer, element = layui.element;
            });
            var jsonData = eval(data);
            var newJson = [];
            for (var secondItem  in  jsonData.tags) {
                // console.log(jsonData.tags[item]['name'])
                var tmpJson = {};
                var sort = Number(jsonData.tags[secondItem]['name'].split('-')[0]);
                tmpJson.sort = sort;
                tmpJson.name = jsonData.tags[secondItem]['name'];
                tmpJson.description = jsonData.tags[secondItem]['description'];
                if (!tmpJson.sort) tmpJson.sort = 0;
                newJson.push(tmpJson)
            }
            // var compare = function (obj1, obj2) {
            //     var val1 = obj1.sort;
            //     var val2 = obj2.sort;
            //     if (val1 < val2) {
            //         return 1;
            //     } else if (val1 > val2) {
            //         return -1;
            //     } else {
            //         return 0;
            //     }
            // };
            newJson.sort(function (a, b) {
                return a.name.localeCompare(b.name, 'zh-CN');
            });
            jsonData.tags = newJson;
            if (jsonData.tags) {
                for (tag in jsonData.tags) {
                    var methods = [];
                    var tagName = jsonData.tags[tag].name;
                    for (pathItem in jsonData.paths) {
                        if (pathItem != "/error") {
                            var methodTag = jsonData.paths[pathItem];
                            for (k in  methodTag) {
                                var methodTagName = methodTag[k].tags[0];
                                if (methodTagName == tagName) {
                                    methodTag[k].type = k;//请求方法
                                    methodTag[k].path = pathItem;//路径
                                    methods.push(methodTag[k]);
                                    continue;
                                }
                            }
                        }
                    }
                    methods.sort(function (a, b) {
                        return a.summary.localeCompare(b.summary, 'zh-CN');
                    });
                    jsonData.tags[tag].methods = methods;
                }
            }

            $("#title").html(jsonData.info.title);
            $("body").html($("#template").render(jsonData));

            $("[name='a_path']").click(function () {
                var path = $(this).attr("path");
                var method = $(this).attr("method");
                var operationId = $(this).attr("operationId");
                $.each(jsonData.paths[path], function (i, d) {
                    // if (d.operationId == operationId) {
                    //     d.path = path;
                    //     d.method = method;
                    //     $("#path-body").html(tempBody.render(d));
                    //     var modelName = getResponseModelName(d.responses["200"]["schema"]["$ref"]);
                    //     if (modelName) {
                    //         $("#path-body-response-model").html(tempBodyResponseModel.render(jsonData.definitions[modelName]));
                    //     }
                    // }
                    if (d.operationId == operationId) {
                        d.path = path;
                        d.method = method;
                        //json参数
                        if (d.parameters && d.parameters[0].hasOwnProperty("schema") && d.parameters[0].schema.$ref) {
                            var objSchema = d.parameters[0].schema;
                            var beanName = getBeanInfo(objSchema, d);
                            if (beanName) {
                                d.bean = jsonData.definitions[beanName].properties;
                                var requiredPrams = jsonData.definitions[beanName].required;
                                //嵌套对象
                                for (secondItem in d.bean) {
                                    var itemSchema = d.bean[secondItem];
                                    var beanName = getBeanInfo(itemSchema, itemSchema);
                                    if (beanName) {
                                        itemSchema.bean = jsonData.definitions[beanName].properties;
                                        var secondItemParams = jsonData.definitions[beanName].required;
                                        for (thirdItem in itemSchema.bean) {
                                            if (secondItemParams && secondItemParams.indexOf(thirdItem) != -1) {
                                                itemSchema.bean[thirdItem].required = true;
                                            }
                                        }
                                    }
                                    if (requiredPrams && requiredPrams.indexOf(secondItem) != -1) {
                                        itemSchema.required = true;
                                    }
                                }
                            }
                        }
                        $("#path-body").html(tempBody.render(d));

                        //如果没有返回值，直接跳过
                        if (!d.responses["200"].hasOwnProperty("schema")) {
                            // continue
                            return true;
                        }

                        //基本类型
                        if (d.responses["200"]["schema"].hasOwnProperty("type")) {
                            var model = {"type": d.responses["200"]["schema"]["type"]};
                            $("#path-body-response-model").append(tempBodyResponseModel.render(model));
                            // continue
                            return true;
                        }

                        //引用类型
                        var modelName = getRefName(d.responses["200"]["schema"]["$ref"]);
                        // if (d.parameters) {
                        //     $.each(d.parameters, function (i, p) {
                        //         if (p["schema"]) {
                        //             // var parameterModelName = getRefName(p["schema"]["$ref"]);
                        //             // renderRefModel("path-body-request-model", jsonData, parameterModelName);
                        //         }
                        //     });
                        // }
                        renderRefModel("path-body-response-model", jsonData, modelName);
                    }
                });
            });


            $("[name='btn_submit']").click(function () {
                var operationId = $(this).attr("operationId");
                var parameterJson = {};
                $("input[operationId='" + operationId + "']").each(function (index, domEle) {
                    var k = $(domEle).attr("name");
                    var v = $(domEle).val();
                    parameterJson.push({k: v});
                });
            });
        }
    });

    $('#tokenSelectId').change(function () {
        $('#Auth_token').val($('#tokenSelectId').val())
    });
});


function getResponseModelName(val) {
    if (!val) {
        return null;
    }
    return val.substring(val.lastIndexOf("/") + 1, val.length);
}


//获得模型名字
function getRefName(val) {
    if (!val) {
        return null;
    }
    return val.substring(val.lastIndexOf("/") + 1, val.length);
}

/**
 * 渲染ref类型参数
 * @param domId 需要添加的domId
 * @param jsonData
 * @param modelName
 */
function renderRefModel(domId, jsonData, modelName) {
    if (modelName) {
        var model = jsonData.definitions[modelName];
        model.name = modelName;
        model.domId = domId;
        if (!model.properties) {
            layer.alert("未定义返回对象属性");
            return;
        }
        //修改有嵌套对象的type
        $.each(model.properties, function (i, v) {
            if (v.items) {
                $.each(v.items, function (j, item) {
                    var typeModel = item.startsWith("#") ? getRefName(item) : item;
                    model.properties[i].type = "Array[" + typeModel + "]";
                });
            }

            //自定义对象类型（非Array）
            if (!v.type) {
                model.properties[i].type = getRefName(v["$ref"]);
            }
        });
        //如果该对象没有被渲染到页面，则渲染
        if ($("#ref-" + domId + "-" + modelName).length == 0) {
            $("#" + domId).append(tempBodyRefModel.render(model));
        }

        //递归渲染多层对象嵌套
        $.each(model.properties, function (i, v) {
            //Array
            if (v.items) {
                $.each(v.items, function (j, item) {

                    if (item.startsWith("#")) {
                        renderRefModel(domId, jsonData, getRefName(item));
                    }
                });
            }

            //单个对象引用
            if (v.hasOwnProperty("$ref")) {
                renderRefModel(domId, jsonData, getRefName(v["$ref"]));
            }

        });
    }
}


//更改参数类型
function changeParameterType(operationId) {
    var v = $("#sel_pt_" + operationId).val();
    if ("form" == v) {
        $("#text_tp_" + operationId).hide();
        $("#table_tp_" + operationId).show();
    } else if ("json" == v) {
        $("#text_tp_" + operationId).show();
        $("#table_tp_" + operationId).hide();
    }
}


function getData(operationId) {
    var path = contextPath + $("[m_operationId='" + operationId + "']").attr("path");
    //path 参数
    $("[p_operationId='" + operationId + "'][in='path']").each(function (index, domEle) {
        var k = $(domEle).attr("name");
        var v = $(domEle).val();
        if (v) {
            path = path.replace("{" + k + "}", v);
        }
    });

    //header参数
    var headerJson = {};
    $("[p_operationId='" + operationId + "'][in='header']").each(function (index, domEle) {
        var k = $(domEle).attr("name");
        var v = $(domEle).val();
        if (v) {
            headerJson[k] = v;
        }
    });

    //请求方式
    var parameterType = $("#sel_pt_" + operationId).val();
    var contentType = $("#sel_pt_" + operationId).find("option:selected").attr("contentType");
    //query 参数
    var parameterJson = {};
    var type = $("[m_operationId='" + operationId + "']").attr("method");
    //发送请求
    var val = $("#Auth_token").val();
    var token = val ? val : "" +
        "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ7XCJhY2NvdW50Tm9uRXhwaXJlZFwiOnRydWUsXCJhY2NvdW50Tm9uTG9ja2VkXCI6dHJ1ZSxcImNyZWRlbnRpYWxzTm9uRXhwaXJlZFwiOnRydWUsXCJlbmFibGVkXCI6dHJ1ZSxcIm9yZ1NpZFwiOjEsXCJzaWRcIjoxLFwidXNlclR5cGVcIjowfSIsImV4cCI6MTYyMzMxOTQyMn0.sim5OAWUVDelHyQZ-psbfp0q1SIfU2yKZZaaIWFaNcPfgoJtAjpYZ20tCOk59Td8EgPNuR5w7Y5rj1A3NuOXRA"
    ;
    headerJson.Auth_token = token;

    if ("form" == parameterType) {
        //如果有文件上传 则提交表单
        if ($("[p_operationId='" + operationId + "'][in='formData']").length > 0) {
            var formData = new FormData($("#form_" + operationId)[0]);
            $.ajax({
                type: type,
                url: path,
                headers: headerJson,
                data: formData,
                dataType: 'json',
                cache: false,
                processData: false,
                contentType: false,
                success: function (data) {
                    var options = {
                        withQuotes: true
                    };
                    $("#json-response").jsonViewer(data, options);
                },
                error:function (data) {
                    var options = {
                        withQuotes: true
                    };
                    $("#json-response").jsonViewer(data.responseJSON, options);
                }
            });
            return;

        } else {
            $("[p_operationId='" + operationId + "'][in='query']").each(function (index, domEle) {
                var k = $(domEle).attr("name");
                var v = $(domEle).val();
                if (v) {
                    parameterJson[k] = v;
                }
            });
            $("[p_operationId='" + operationId + "'][in='body']").each(function (index, domEle) {
                var k = $(domEle).attr("name");
                var v = $(domEle).val();
                if (v) {
                    parameterJson[k] = v;
                }
            });
        }

    } else if ("json" == parameterType) {
        contentType = "application/json";
        parameterJson = $("#text_tp_" + operationId).val();
    }

    $.ajax({
        type: type,
        url: path,
        data: parameterJson,
        headers: headerJson,
        dataType: 'json',
        contentType: contentType,
        success: function (data) {
            var options = {
                withQuotes: true
            };
            $("#json-response").jsonViewer(data, options);
        },
        error:function (data) {
            var options = {
                withQuotes: true
            };
            $("#json-response").jsonViewer(data.responseJSON, options);
        }
    });
}


function getBeanInfo(obj, resultObj) {
    var beanName = "";
    if (obj.items) {
        beanName = obj.items.$ref.split('definitions/')[1];
        resultObj.isArray = true;
    } else if (obj.$ref) {
        beanName = obj.$ref.split('definitions/')[1];
    }
    return beanName;
}



