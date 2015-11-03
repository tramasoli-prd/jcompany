'use strict';

/* TODO criar um gulp plugin */

var gutil = require('gulp-util');

var defaultEnv = null;
var currentEnv = null;

var envs = {};

module.exports = {

    config: function(options) {

        defaultEnv = options.defaultEnv || 'defaults';
        currentEnv = options.currentEnv || gutil.env.env || defaultEnv;

        if (options.envs) {
            for (var name in options.envs) {
                envs[name] = options.envs[name];
            }
        }
    },

    get: function(name, defaultValue) {
        // defined in command line override all
        if (gutil.env.hasOwnProperty(name)) {
            return gutil.env[name];
        }
        // defined env?
        var env = envs[currentEnv];
        if (env && env.hasOwnProperty(name)) {
            return env[name];
        }
        // defined in default env?
        var def = envs[defaultEnv];
        if (def && def.hasOwnProperty(name)) {
            return def[name];
        }
        return defaultValue;
    },

    log: function() {
        
        var env = envs[currentEnv];
        var def = envs[defaultEnv];
        var log = function(env, name, value) {
            gutil.log(
                gutil.colors.yellow('Env'),
                gutil.colors.green(env),
                gutil.colors.cyan('"' + name + '"'),
                gutil.colors.yellow('=>'),
                gutil.colors.cyan(value)
            );
        };

        gutil.log(gutil.colors.red('*** Default Env: ' + currentEnv));

        for (var name in env) {
            log(currentEnv, name, env[name]);
        }
        for (var name in def) {
            if (!env.hasOwnProperty(name)) {
                log(defaultEnv, name, def[name]);
            }
        }
    },

    logEnvs: function() {

        for (var envName in envs) {

            var env = envs[envName];

            gutil.log('--- Env: ' + envName);

            for (var name in env) {
                gutil.log('=> Env entry: "' + name + '" = ' + env[name]);
            }
        }
    }
};
