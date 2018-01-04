var app = angular.module('taskApp',['ui.router','ngStorage']);

app.constant('urls', {
    BASE: 'http://localhost:8080/EmiratesTaskBoard',
    USER_SERVICE_API : 'http://localhost:8080/EmiratesTaskBoard/api/task/'
});

app.config(['$stateProvider', '$urlRouterProvider',
    function($stateProvider, $urlRouterProvider) {

        $stateProvider
            .state('home', {
                url: '/',
                templateUrl: 'partials/list',
                controller:'TaskController',
                controllerAs:'ctrl',
                resolve: {
                    tasks: function ($q, TaskService) {
                        console.log('Load all tasks');
                        var deferred = $q.defer();
                        TaskService.loadAllTasks().then(deferred.resolve, deferred.resolve);
                        return deferred.promise;
                    }
                }
            });
        $urlRouterProvider.otherwise('/');
    }]);

