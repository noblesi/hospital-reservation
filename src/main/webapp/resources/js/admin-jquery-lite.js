(function(window, document) {
    "use strict";

    if (window.jQuery && window.$) {
        return;
    }

    function toArray(value) {
        if (!value) {
            return [];
        }
        if (value instanceof Wrapper) {
            return value.elements;
        }
        if (Array.isArray(value)) {
            return value;
        }
        if (value instanceof NodeList || value instanceof HTMLCollection) {
            return Array.prototype.slice.call(value);
        }
        return [value];
    }

    function parseHtml(html) {
        var template = document.createElement("template");
        template.innerHTML = html.trim();
        return Array.prototype.slice.call(template.content.childNodes);
    }

    function Wrapper(elements) {
        this.elements = elements || [];
        this.length = this.elements.length;
        for (var i = 0; i < this.elements.length; i++) {
            this[i] = this.elements[i];
        }
    }

    Wrapper.prototype.each = function(callback) {
        this.elements.forEach(function(element, index) {
            callback.call(element, index, element);
        });
        return this;
    };

    Wrapper.prototype.on = function(eventName, handler) {
        return this.each(function() {
            this.addEventListener(eventName, handler);
        });
    };

    ["click", "change", "keydown"].forEach(function(eventName) {
        Wrapper.prototype[eventName] = function(handler) {
            if (typeof handler === "function") {
                return this.on(eventName, handler);
            }
            return this.trigger(eventName);
        };
    });

    Wrapper.prototype.trigger = function(eventName) {
        return this.each(function() {
            this.dispatchEvent(new Event(eventName, { bubbles: true }));
        });
    };

    Wrapper.prototype.val = function(value) {
        if (value === undefined) {
            return this.elements[0] ? this.elements[0].value : undefined;
        }
        return this.each(function() {
            this.value = value;
        });
    };

    Wrapper.prototype.focus = function() {
        if (this.elements[0]) {
            this.elements[0].focus();
        }
        return this;
    };

    Wrapper.prototype.hide = function() {
        return this.each(function() {
            this.style.display = "none";
        });
    };

    Wrapper.prototype.show = function() {
        return this.each(function() {
            this.style.display = "";
        });
    };

    Wrapper.prototype.empty = function() {
        return this.each(function() {
            this.innerHTML = "";
        });
    };

    Wrapper.prototype.append = function(content) {
        return this.each(function() {
            var target = this;
            if (typeof content === "string") {
                parseHtml(content).forEach(function(node) {
                    target.appendChild(node);
                });
                return;
            }
            toArray(content).forEach(function(node) {
                target.appendChild(node.cloneNode ? node.cloneNode(true) : node);
            });
        });
    };

    Wrapper.prototype.remove = function() {
        return this.each(function() {
            if (this.parentNode) {
                this.parentNode.removeChild(this);
            }
        });
    };

    Wrapper.prototype.last = function() {
        return new Wrapper(this.elements.length ? [this.elements[this.elements.length - 1]] : []);
    };

    Wrapper.prototype.eq = function(index) {
        return new Wrapper(this.elements[index] ? [this.elements[index]] : []);
    };

    Wrapper.prototype.index = function(element) {
        var target = element instanceof Wrapper ? element.elements[0] : element;
        return this.elements.indexOf(target);
    };

    Wrapper.prototype.find = function(selector) {
        var found = [];
        this.elements.forEach(function(element) {
            found = found.concat(Array.prototype.slice.call(element.querySelectorAll(selector)));
        });
        return new Wrapper(found);
    };

    Wrapper.prototype.css = function(property, value) {
        return this.each(function() {
            this.style[property] = value;
        });
    };

    Wrapper.prototype.addClass = function(className) {
        return this.each(function() {
            this.classList.add(className);
        });
    };

    Wrapper.prototype.fadeOut = function() {
        return this.hide();
    };

    Wrapper.prototype.fadeIn = function() {
        return this.show();
    };

    function $(selector) {
        if (typeof selector === "function") {
            if (document.readyState === "loading") {
                document.addEventListener("DOMContentLoaded", selector);
            } else {
                selector();
            }
            return new Wrapper([]);
        }
        if (typeof selector === "string") {
            return new Wrapper(Array.prototype.slice.call(document.querySelectorAll(selector)));
        }
        return new Wrapper(toArray(selector));
    }

    $.each = function(items, callback) {
        if (!items) {
            return;
        }
        Array.prototype.slice.call(items).forEach(function(item, index) {
            callback.call(item, index, item);
        });
    };

    $.hasParams = function() {
        return window.location.search.length > 1;
    };

    window.jQuery = window.$ = $;
})(window, document);
