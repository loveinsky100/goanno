# goanno
Goanno is a plugin for goland  
This plugin provides auto generate comments for golang

# How to install
1.goland plugins marketplace(search Goanno)  
2.download release jar(goland install from disk) [goanno.jar](https://github.com/loveinsky100/goanno/releases)

# How to use
1.control + commond + /  (for windows: control + alt + /)  
2.Right click -> Generate -> Goanno  
3.Edit comment template  
+ 1.Select "Tools - Goanno Setting"  
+ 2.Edit template

# Template

| Args | Desc |
| --- | --- |
| ${todo} | Blank Placeholder |
| ${receiver} | function receiver name or type |
| ${params} | function params name or type|
| ${return_types} | function output name or type |
| ${function_name} | function name |
| ${date} | yyyy-MM-dd HH:mm:ss |

## V2.1.0
| Args | Desc |
| --- | --- |
| ${todo} | Blank Placeholder |
| ${receiver} | function receiver name or type |
| ${params} | function params name or type|
| ${return_types} | function output name or type |
| ${function_name} | function name |
| ${param_name} | function params name |
| ${param_type} | function params type |
| ${param_name_type} | function params name and type |
| ${receiver_name} | function receiver name |
| ${receiver_type} | function receiver type |
| ${receiver_name_type} | function receiver name and type |
| ${ret} | function output name or type, equals to ${return_types} |
| ${ret_name} | function output name |
| ${ret_type} | function output type |
| ${ret_name_type} | return name and type |
| ~~${note}~~ | ~~previous comments (NOT SUPPORT, dev...)~~ |

## V3.0.0
New Config Support

![](https://raw.githubusercontent.com/loveinsky100/goanno/dev/preview/goanno_v3_setting.png)

| Args | Desc |
| --- | --- |
| ${interface_name} | Name of Interface |
| ${struct_name} | Name of Struct |
| ${struct_field_name} | Name of Struct Field |
| ${package_name} | Name of Package |


Suggest Templates  
java style(default)
```
/*
 * @Description: ${todo}
 * @receiver ${receiver}
 * @param ${params}
 * @return ${return_types}
 */
```

golang style
```
//
// @Description: ${todo}
// @receiver ${receiver}
// @param ${params}
// @return ${return_types}
//
```

## V2.2.0
Supports rewrite previous comments / 支持覆盖原有注释

Setting: "Tools - Goanno Setting - Select Checkbox"  
![](https://raw.githubusercontent.com/loveinsky100/goanno/dev/preview/goanno_cover.gif)

# How to build
```
Project SDK: IntelliJ IDEA Community Edition IC-202.8194.7
JDK: 1.8
IDEA Version: IntelliJ IDEA 2020.2.4 (Community Edition)

Tip: Do not use 2020.3
```

Test File:
```
resource
    - test.go
    - test2.go
src/org/leo/goanno/test
    - GenerateTest.java
```

# Preview

![](https://raw.githubusercontent.com/loveinsky100/goanno/master/preview/goanno_func.gif)
![](https://raw.githubusercontent.com/loveinsky100/goanno/master/preview/goanno_interface.gif)
![](https://raw.githubusercontent.com/loveinsky100/goanno/dev/preview/goanno_template_setting.gif)
