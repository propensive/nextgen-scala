
examples: dotty/dist-bootstrapped/target/pack/bin/dotc bin/
	$< -d bin -explain examples/*/*.scala

bin/:
	mkdir -p bin

scala:
	git clone --recursive https://github.com/scala/scala.git scala

dotty:
	git clone --recursive https://github.com/lampepfl/dotty.git dotty

dotty/dist-bootstrapped/target/pack/bin/dotc: dotty
	cd dotty && sbt dist-bootstrapped/pack && cd .. && touch $@

repl: dotty/dist-bootstrapped/target/pack/bin/dotc
	dotty/dist-bootstrapped/target/pack/bin/dotr

scala/build/quick/bin/scalac: scala
	cd scala && sbt dist/mkBin && cd .. && touch $@

tests:
	mkdir -p tests
	bash etc/buildtests.sh

tests/%: tests
	@bash etc/compare.sh $(@F)
	
compilers: dotty/dist-bootstrapped/target/pack/bin/dotc scala/build/quick/bin/scalac
	ln -fs dotty/dist-bootstrapped/target/pack/bin/dotc dotc
	ln -fs scala/build/quick/bin/scalac scalac
	ln -fs scala/build/quick/bin/fsc fsc

ALLTESTS = $(wildcard tests/*)

all: tests $(ALLTESTS)

.PHONY: compilers examples repl
