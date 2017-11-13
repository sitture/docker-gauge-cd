# base Image
FROM ubuntu:16.04

# install packages required to run app and tests
RUN apt-get update \
        && apt-get install -y libicu-dev git zlib1g-dev software-properties-common xvfb  \
        && apt-get clean \
        && rm -rf /var/lib/apt/lists/*

# install java
RUN add-apt-repository -y ppa:webupd8team/java
RUN apt-get update
RUN apt-get install -y debconf-utils
RUN \
  echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | debconf-set-selections && \
  apt-get install -y oracle-java8-installer && \
  rm -rf /var/lib/apt/lists/* && \
  rm -rf /var/cache/oracle-jdk8-installer

# Define commonly used JAVA_HOME variable
ENV JAVA_HOME /usr/lib/jvm/java-8-oracle

# install gauge
RUN apt-key adv --keyserver hkp://pool.sks-keyservers.net --recv-keys 023EDB0B
RUN echo deb http://dl.bintray.com/gauge/gauge-deb stable main | tee -a /etc/apt/sources.list
RUN apt-get update && \
  apt-get upgrade -y && \
  apt-get install -y maven ant curl vim git wget libfreetype6 libfontconfig bzip2

RUN apt-get install -y gauge

# copy files to container
COPY . /usr/src/app/functional
WORKDIR /usr/src/app/functional

# install gauge plugins
RUN gauge telemetry off && \
  gauge install java && \
  gauge install html-report && \
  gauge install screenshot && \
  gauge install xml-report

# install the maven project
RUN mvn clean install -Pdocker

ENTRYPOINT ["mvn"]
CMD ["compile"]
