var gulp = require("gulp"),
	htmlhint = require("gulp-htmlhint"),
	sourcemaps = require("gulp-sourcemaps"),
	autoprefixer = require("gulp-autoprefixer"),
	cleancss = require("gulp-clean-css"),
	concat = require("gulp-concat"),
	jshint = require("gulp-jshint"),
	uglify = require("gulp-uglify");


const PATHS = {
	EXTERNALS : {
		SRC: "./bower_components/",
		DEST: "./wwwroot/lib/"
	},
	HTML : {
		SRC: "./wwwroot/**/*.html"
	},
	CSS : {
		SRC: "./app/css/**/*.css",
		DEST: "./wwwroot/css/"
	},
	JS : {
		SRC: "./app/js/**/*.js",
		DEST: "./wwwroot/js/"
	}
};

gulp.task("default", function(){

	var htmlwatcher = gulp.watch(PATHS.HTML.SRC, ['html-validate']);
	var csswatcher = gulp.watch(PATHS.CSS.SRC, ['css-validate']);
	var jswatcher = gulp.watch(PATHS.JS.SRC, ['js-validate']);

	csswatcher.on("change", function(event){
		console.log("file: " + event.path + " was " + event.type);
	});
});


const autoprefixeroptions = {
	browsers: ['last 2 versions']
};

gulp.task("css", function(){

	gulp.src(PATHS.CSS.SRC)
		.pipe(sourcemaps.init())
		.pipe(autoprefixer(autoprefixeroptions))
		.pipe(concat("main.min.css"))
		.pipe(cleancss({ debug: true, compactibility: '*'},
			function(details){
				console.log(details.name + ": " + details.stats.originalSize);
				console.log(details.name + ": " + details.stats.minifiedSize);
			}
		))
		.pipe(sourcemaps.write())
		.pipe(gulp.dest(PATHS.CSS.DEST));
});

gulp.task("js", function(){

	gulp.src(PATHS.JS.SRC)
		.pipe(jshint(".jshintrc"))
		.pipe(htmlhint.reporter("jshint-stylish"))
		.pipe(sourcemaps.init())
		.pipe(concat("main.min.js"))
		.pipe(uglify({ debug: true, compactibility: "*"},
			function(details) {	
				console.log(details.name + ": " + details.stats.originalSize);
				console.log(details.name + ": " + details.stats.minifiedSize);
			}
		))
		.pipe(sourcemaps.write())
		.pipe(gulp.dest(PATHS.JS.DEST));
});

gulp.task("html-validate", function(){
	gulp.src(PATHS.HTML.SRC)
		.pipe(htmlhint(".htmlhintrc"))
		.pipe(htmlhint.reporter("htmlhint-stylish"))
		;//.pipe(htmlhint.failReporter());
});

gulp.task("copy-externals", function(){

	gulp.src(PATHS.EXTERNALS.SRC + "bootstrap/dist/**")
		.pipe(gulp.dest(PATHS.EXTERNALS.DEST + "bootstrap"));
});