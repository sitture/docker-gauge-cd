# docker-gauge-cd
[![Build Status](https://travis-ci.org/sitture/docker-gauge-cd.svg?branch=master)](https://travis-ci.org/sitture/docker-gauge-cd)

An example project to run gauge-java tests within a container when main web application is also a docker container.

The project uses this simple [sitture/docker-cd-seed](https://github.com/sitture/docker-cd-seed) application and runs `gauge-java` tests from a docker container.

## Prerequisite

* Install Docker from [here](https://www.docker.com)

## Running

Run the following to bring up the both `web` and `sut` containers (in background).

```bash
docker-compose build
docker-compose up -d
```

The above will bring up both the `web` container at `http://localhost:8080` and `sut` container will compile the maven based gauge project and exit.

## Gauge Container

The gauge container `sut` is run from an existing docker-gauge image from Hub. See [sitture/docker-gauge-java](https://github.com/sitture/docker-gauge-java).

Run the following to run gauge tests from maven. The entry point command for `sut` container is `mvn`.

```bash
docker-compose run sut test
```

The above is equivalent to running `mvn test`

## Contributing

1. Fork it
2. Create your feature branch (`git checkout -b my-new-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin my-new-feature`)
5. Create new Pull Request
