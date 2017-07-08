FROM docker.elastic.co/elasticsearch/elasticsearch:5.4.1
ADD elasticsearch.yml /User/oguzhankaracullu/es-docker/config/
USER root
RUN chown elasticsearch:elasticsearch config/elasticsearch.yml
USER elasticsearch
