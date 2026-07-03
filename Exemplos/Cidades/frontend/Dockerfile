FROM nginx:stable-alpine
COPY root /usr/share/nginx/html
COPY default.conf.template /opt/default.conf.template
COPY 04-envsubst.sh /docker-entrypoint.d/04-envsubst.sh
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]