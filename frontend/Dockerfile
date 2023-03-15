FROM node:18.15-alpine3.17 as builder
WORKDIR /app
COPY . /app
RUN npm install
RUN npx ng build

FROM nginx:1.23.3-alpine
COPY ./nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=builder /app/dist/frontend /usr/share/nginx/html
