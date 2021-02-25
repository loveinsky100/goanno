# goanno
Goanno is a plugin for goland  
This plugin provides auto generate comments for golang

# How to install
1.goland plugins marketplace(search Goanno)  
2.download release jar(goland install from disk) [goanno.jar](https://github.com/loveinsky100/goanno/releases/tag/1.0.0)

# How to use
1.control + commond + /  
2.Right click -> Generate -> Goanno  
3.Edit comment template  
+ 1.Select "Tools - Goanno Setting"  
+ 2.Edit template

# Template

| Args | Desc |
| --- | --- |
| ${todo} | Blank Placeholder |
| ${receiver} | function receiver |
| ${params} | function params |
| ${return_types} | function return |
| ${function_name} | function name |
| ${date} | yyyy-MM-dd HH:mm:ss |

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
