FROM node:lts-alpine AS build-stage
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN rm .env vue.config.js
RUN npm run build

# estágio de produção
FROM nginx:stable-alpine AS production-stage
COPY --from=build-stage /app/dist /usr/share/nginx/html
COPY default.conf.template /opt/default.conf.template
COPY 04-envsubst.sh /docker-entrypoint.d/04-envsubst.sh

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
