FROM node:18.20.4

# Set the working directory
WORKDIR /usr/src/app

ENV TZ=America/Sao_Paulo

# Copy package.json and package-lock.json
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the application files
COPY . .

# Start the application
CMD [ "npm", "start" ]