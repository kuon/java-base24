MDCAT:=$(firstword $(shell which mdcat cat 2>/dev/null))

.PHONY: help
help:
	$(MDCAT) MAKE.md

.PHONY: readme
readme:
	$(MDCAT) README.md

.PHONY: build
build:
	./gradlew build

.PHONY: test
test:
	./gradlew test

.PHONY: clean
clean:
	rm -fr build .gradle


.PHONY: publish
publish:
	gradle bintrayUpload

