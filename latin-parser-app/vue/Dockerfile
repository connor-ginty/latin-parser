# Stage 1: Build the application
FROM node:16 AS build
WORKDIR /latin-parser/latin-parser-app/vue

# Copy package.json and install dependencies
COPY package*.json ./
RUN npm install

# Copy the source code and build the application
# COPY . .
RUN npm run serve

# Stage 2: Serve the application
FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html

# Expose the port the app runs on
EXPOSE 80

# Start Nginx server
CMD ["nginx", "-g", "daemon off;"]