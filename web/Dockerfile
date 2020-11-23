FROM node:latest

WORKDIR /app

COPY package*.json /app/

RUN npm install

COPY ./ /app/

RUN npm run build


FROM nginx:latest

WORKDIR /app

COPY ./build /usr/share/nginx/html/