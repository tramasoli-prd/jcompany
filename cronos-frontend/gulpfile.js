// common
var gulp = require('gulp');
var gutil = require('gulp-util');
var gulpif = require('gulp-if');
var concat = require('gulp-concat');
var rename = require('gulp-rename');
var changed = require('gulp-changed');
var tokenReplace = require('gulp-token-replace');
var sourcemaps = require('gulp-sourcemaps');
var watch = require('gulp-watch');
var rev = require('gulp-rev');
var del = require('del');
// js
var jshint = require('gulp-jshint');
var uglify = require('gulp-uglify');
var stripDebug = require('gulp-strip-debug');
var ngAnnotate = require('gulp-ng-annotate');
var angularTemplateCache = require('gulp-angular-templatecache');
// css
var autoprefix = require('gulp-autoprefixer');
var minifyCSS = require('gulp-minify-css');
// html
var minifyHTML = require('gulp-minify-html');
var inject = require('gulp-inject');
var include = require('gulp-include');
// deploy
var zip = require('gulp-zip');
var unzip = require('gulp-unzip');
// server
var server = require('gulp-connect');
// livereload
var livereload = require('gulp-livereload');

// Plugin para configuração de ambientes
var enviroments = require('./gulp-env/enviroments');

/*=============================================================
 *
 * Configuration
 *
 =============================================================*/

var timestamp = (new Date().getTime()).toString();
var name = 'cronos';
var build = 'build';
var dist = 'dist';
var version = '1.0.0';
var buildNumber = timestamp;
var buildVersion = version + '-' + buildNumber;
var zipName = name + '-' + buildVersion + '.zip';
var zipNameBackup = name + '-' + buildNumber + '-backup.zip';
var remoteDeployServer = '//w0319765/Desenv/cronos';

enviroments.config({

    /* env properties */
    defaultEnv: 'local',

    envs: {
    	'local': {
            contextUrl: '/',
            backendUrl: 'http://localhost:7001/cronos-service'
        },
        'back-test': {
            backendUrl: 'http://w0319765:7001/cronos-service'
        },
        'test': {
            contextUrl: '/cronos/',
            backendUrl: 'http://w0319765:7001/cronos-service'
        },
        'desenv': {
            contextUrl: '/sistemas/cronos/',
            backendUrl: 'https://www.desenv.tjrs.jus.br/cronos-service'
        },
        'homolog' : {
            contextUrl: '/sistemas/cronos/',
            backendUrl: 'https://www.homolog.tjrs.jus.br/cronos-service'
        }
    }
});

var config = {

    source: {
        debug: true,
        min: false,
        map: false
    },

    server: {
        port: 8000
    },

    assets: {
        src: ['src/assets/**/*'],
        dest: build + '/assets/'
    },
    images: {
        src: ['src/images/**/*'],
        dest: build + '/images/'
    },
    scripts: {
        src: ['src/scripts/**/*.js'],
        dest: build + '/scripts/',
        concat: 'app.js'
    },
    styles: {
        src: ['src/styles/**/*.css'],
        dest: build + '/styles/',
        concat: 'app.css'
    },
    html: {
        src: ['src/**/*.html', '!src/{includes,includes/**}']
    }
};

/*=============================================================
 *
 * Configuration to replace TOKENS in source code in build
 *
 =============================================================*/
var enviromentTokens = {

    prefix: '#{',
    suffix: '}',

    log: function() {
        var tokens = this.tokens;
        gutil.log(gutil.colors.red('*** Tokens to build replace:'));
        for (var name in tokens) {
            gutil.log(
                gutil.colors.yellow('Token'),
                gutil.colors.cyan('#{' + name + '}'),
                gutil.colors.yellow('=>'),
                gutil.colors.cyan(tokens[name])
            );
        }
    },

    tokens: {

        version: buildVersion,

        contextUrl: enviroments.get('contextUrl'),

        backendUrl: enviroments.get('backendUrl')
    }
};

/*=============================================================
 *
 * TASKS definitions
 *
 ==============================================================*/

// Task Groups
gulp.task('default', ['log', 'build', 'watch', 'server']);
gulp.task('build', ['clear', 'log'], function() {
    setTimeout(function() {
        gulp.start(['assets', 'images', 'scripts', 'styles', 'html']);
    }, 2000);
});
gulp.task('deploy', function() {
    gulp.start('dist', function() {
        gutil.log('deploying... => ' + remoteDeployServer + '/' + zipName);

        gulp.src(dist + '/' + zipName)
            .pipe(gulp.dest(remoteDeployServer))
            .pipe(unzip())
            .pipe(gulp.dest(remoteDeployServer + '/build'));
    });
});

// Task alias
gulp.task('cls', ['clear']);

/*
 * Log envs
 */
gulp.task('envs', function() {
    enviroments.logEnvs();
});
gulp.task('log', function() {
    enviroments.log();
    enviromentTokens.log();
});

/*
 * Clear build
 */
gulp.task('clear', function() {
    del(build);
    del(dist);
});

/*
 * Vendor Task: Vendor files
 */
gulp.task('assets', function() {
    return gulp.src(config.assets.src)
        .pipe(changed(build))
        .pipe(gulp.dest(config.assets.dest));
});

/*
 * Images Task: Build imagens
 */
gulp.task('images', function() {
    return gulp.src(config.images.src)
        .pipe(changed(build))
        .pipe(gulp.dest(config.images.dest));
});

/*
 * Script Task: JS concat, strip debugging and minify
 */
gulp.task('scripts', function() {

    var finalName = config.scripts.concat;

    del(config.scripts.dest + finalName.replace('.js', '*'));

    // copy and minify all
    return gulp.src(config.scripts.src)
        .pipe(gulpif(config.source.map, sourcemaps.init())) // add info about origin file for debug purpose
        .pipe(tokenReplace(enviromentTokens))
        .pipe(jshint())
        .pipe(jshint.reporter('default'))
        .pipe(concat(finalName))
        .pipe(ngAnnotate())
        .pipe(gulpif(!config.source.debug, stripDebug()))
        .pipe(gulpif(config.source.min, uglify()))
        .pipe(gulpif(config.source.map, sourcemaps.write()))
        .pipe(gulp.dest(config.scripts.dest))
        .pipe(livereload());
});


/*
 * Styles Task: CSS concat, auto-prefix and minify
 */
gulp.task('styles', function() {

    var finalName = config.styles.concat;

    del(config.styles.dest + finalName.replace('.css', '*'));

    return gulp.src(config.styles.src)
        .pipe(gulpif(config.source.map, sourcemaps.init())) // add info abaut origin file for debug purpose
        .pipe(concat(finalName))
        .pipe(tokenReplace(enviromentTokens))
        .pipe(autoprefix(['last 2 versions', 'safari 5', 'ie 9', 'opera 12.1', 'ios 6', 'android 4']))
        .pipe(gulpif(config.source.min, minifyCSS()))
        .pipe(gulpif(config.source.map, sourcemaps.write()))
        .pipe(gulp.dest(config.styles.dest))
        .pipe(livereload());
});


/*
 * HTML Tasks: minify new or changed HTML pages
 */
gulp.task('html', function() {

    return gulp.src(config.html.src)
        .pipe(changed(build))
        .pipe(include())
        .pipe(gulpif(config.source.min, minifyHTML()))
        .pipe(tokenReplace(enviromentTokens))
        .pipe(gulp.dest(build))
        .pipe(livereload());
});

/*
 * ZIP the build directory
 */
gulp.task('dist', function() {
    return gulp.src(build + '/**/*')
        .pipe(zip(zipName))
        .pipe(gulp.dest(dist));
});

/*
 * ZIP the build directory into WAR file
 */
gulp.task('war-info', function() {
    // copy WAR info
    return gulp.src('war/**/*')
        .pipe(gulp.dest(build));
});
gulp.task('war', ['war-info'], function() {
    // create WAR zip
    return gulp.src(build + '/**/*')
        .pipe(zip(name + '.war'))
        .pipe(gulp.dest(dist));
});

/*
 * Server Tasks
 */
gulp.task('server', function() {
    server.server({
        root: build,
        port: config.server.port,
        livereload: true
    });
});

/*
 * Update Task: process modifications in project.
 */
gulp.task('watch', function() {
    livereload.listen();
    watch(config.scripts.src, function(event) {
        gulp.start(['scripts', 'html']);
    });
    watch(config.styles.src, function(event) {
        gulp.start(['styles', 'html']);
    });
    watch(config.html.src, function(event) {
        gulp.start('html');
    });
});

// Exporta para o Node.js, assim eh possivel extender (herdar) para outros nodulos.
module.exports = gulp;
