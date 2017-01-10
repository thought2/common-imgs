# common-imgs

Clojure library to retrieve random images from wikimedia commons.

## Usage

Add `[t2/common-imgs "0.1.0-SNAPSHOT"]` to your dependencies.

```clj
(require '[t2.common-imgs :refer [random-spec spec->url])

(def spec (random-spec))
```

This returns an image spec:

```clj
{:size [999 749],
 :thumb-url "https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/Cuban_Embassy_in_Wellington.jpg/120px-Cuban_Embassy_in_Wellington.jpg",
 :url-template ["https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/Cuban_Embassy_in_Wellington.jpg/" "px-Cuban_Embassy_in_Wellington.jpg"]}
```

When this spec is passed to `spec->url` including a width parameter, one will get back an url which can be used to download the image in this size:

```clj
(spec->url spec)
;; "https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/Cuban_Embassy_in_Wellington.jpg/500px-Cuban_Embassy_in_Wellington.jpg"
```

