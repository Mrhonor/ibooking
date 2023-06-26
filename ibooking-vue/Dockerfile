# 设置基础镜像
FROM node:14 as build-stage

# 设置工作目录
WORKDIR /app

# 复制项目的 package.json 和 package-lock.json 到工作目录
COPY package*.json ./

# 安装项目依赖
RUN npm install

# 复制整个项目到工作目录
COPY . .

# 构建前端项目
RUN npm run build

# 设置新的基础镜像，这次选择 Nginx
FROM nginx:latest

# 复制构建后的前端项目到 Nginx 的默认静态文件目录
COPY --from=build-stage /app/dist /usr/share/nginx/html
# COPY --from=build-stage /app/dist/  /usr/share/nginx/html/
# COPY --from=build-stage /app/nginx/default.conf /etc/nginx/conf.d/default.conf

# 暴露 Nginx 的默认端口
EXPOSE 80

# CMD ["npm", "run", "serve"]
# 在容器启动时启动 Nginx 服务
CMD ["nginx", "-g", "daemon off;"]

