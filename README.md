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


![](https://raw.githubusercontent.com/loveinsky100/goanno/master/preview/goanno_func.gif)
![](https://raw.githubusercontent.com/loveinsky100/goanno/master/preview/goanno_interface.gif)
![](https://raw.githubusercontent.com/loveinsky100/goanno/dev/preview/goanno_template_setting.gif)
