// phantom.js script


"use strict";
var page = require('webpage').create();
page.viewportSize = { width: 70, height : 70 };

page.content = '<html><body><canvas id="display"></canvas></body></html>';

var draw = function() {
    var canvas = document.getElementById("display");
    var ctx = canvas.getContext("2d");

    var size = 70;
    var border = .11;
    var minRectSize = .4;
    var maxRectSize = .65;
    var nRects = 4;
    var rectHue = .57;
    var bgHue = .04;
    var n = 4;

    var half_pi = Math.PI * .5;

    var hslaStr = function(h, s, l, a) {
	var h = h*255;
	var s = s*100;
	var l = l*100;
	return "hsla(" + h + "," + s + "%," + l + "%," + a + ")";
    }
	
    var drawBg = function () {
	var bgSize = 1 - border;
	ctx.fillStyle = hslaStr(bgHue, 1, .4, 1);
	ctx.fillRect(0, 0, bgSize, bgSize);
    }
    
    var drawRects = function () {
	var a = border/2;
	var b = 1 - maxRectSize;
	for (var i = 1; i <= n; i++) {
	    var fac = i/n;
	    var perspFac = 1 - Math.sin(half_pi * (1 - fac));
	    var pos = a + ((b - a) *  perspFac);
	    var dist = maxRectSize - minRectSize;
	    var size = minRectSize + (dist * perspFac);
	    var light = (.6 - fac * .38);
	    var sat = .5;
	    ctx.fillStyle = hslaStr(rectHue, sat, light, 1);
	    ctx.fillRect(pos, pos, size, size);
	}
    }

    var init = function () {
	canvas.width = size;
	canvas.height = size;
	ctx.scale(size, size);
    }

    init();
    drawBg();
    drawRects();
}

page.evaluate(draw);

page.render('common-imgs-logo.png');
phantom.exit();
