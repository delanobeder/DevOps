FROM node:16.14.0-alpine 

# Set the working directory
WORKDIR /usr/src/app

ENV TZ=America/Sao_Paulo

# Copy package.json and package-lock.json
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the application files
COPY . .

# Expose the port
EXPOSE 3000

# Start the application
CMD [ "node", "app.js" ]