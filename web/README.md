# 前端开发

### 本地安装并使用sullay-blog
``` js
git clone https://github.com/sullay/sullay-blog.git
cd sullay-blog/web
npm i
```

### 启动项目
``` js
npm run dev
```

### 打包项目
``` js
npm run build
```
### 目录结构
- assets：静态文件 css、img
- components：vue组件
- config：配置文件 （本地开发需要改为本地ip+port，fileConfig.js baseUrl为文件服务器地址，需要自行准备建议使用nginx）
- plugins：插件
- router：前端路由
- service：api接口
- store：vuex
- views：vue视图