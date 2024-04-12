FROM ibmjava:jre
RUN mkdir /opt/app
ENV TZ=America/Sao_Paulo
COPY target/AloMundoMVC-0.0.1-SNAPSHOT.jar /opt/app/release.jar
CMD ["java", "-jar", "/opt/app/release.jar"]

