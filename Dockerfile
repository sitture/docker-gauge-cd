FROM node:argon

RUN useradd -ms /bin/bash web
RUN mkdir -p /usr/src/app

WORKDIR /usr/src/app
COPY package.json /tmp
RUN cd /tmp && npm install
COPY . /usr/src/app
RUN cp -R /tmp/node_modules /usr/src/app
RUN chown -R web:web /usr/src/app
USER web

EXPOSE 8080

CMD [ "npm", "start" ]
