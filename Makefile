deps:
	brew install clojure
	brew install leiningen

/usr/local/bin/cljfmt:
	@/bin/bash -c "$$(curl -fsSL https://raw.githubusercontent.com/weavejester/cljfmt/HEAD/install.sh)"

cljfmt-check: /usr/local/bin/cljfmt
	@cljfmt check src test

cljfmt-fix: /usr/local/bin/cljfmt
	@cljfmt fix src test

doc/%.png: doc/%.mmd
	mmdc -i $< -o $@

.PHONY: docs
docs: $(patsubst doc/%.mmd,doc/%.png,$(wildcard doc/*.mmd))
