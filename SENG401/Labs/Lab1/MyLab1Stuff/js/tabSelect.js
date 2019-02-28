var items = document.querySelectorAll('#i8pt56');
		for (var i = 0, len = items.length; i < len; i++) {
			(function() {
				var t, e = this, a = "[data-tab]", n = document.body, r = n.matchesSelector
						|| n.webkitMatchesSelector
						|| n.mozMatchesSelector
						|| n.msMatchesSelector, o = function() {
					var a = e.querySelectorAll("[data-tab-content]") || [];
					for (t = 0; t < a.length; t++)
						a[t].style.display = "none"
				}, i = function(n) {
					var r = e.querySelectorAll(a) || [];
					for (t = 0; t < r.length; t++) {
						var i = r[t], s = i.className.replace("tab-active", "")
								.trim();
						i.className = s
					}
					o(), n.className += " tab-active";
					var l = n.getAttribute("href"), c = e.querySelector(l);
					c && (c.style.display = "")
				}, s = e.querySelector(".tab-active" + a);
				s = s || e.querySelector(a), s && i(s), e.addEventListener(
						"click", function(t) {
							var e = t.target;
							r.call(e, a) && i(e)
						})
			}.bind(items[i]))();
		}