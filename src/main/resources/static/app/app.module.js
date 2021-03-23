var app=angular.module('toDoListApp',['ngRoute', 'ui.bootstrap', 'ngMaterial', 'ngMessages', 'material.svgAssetsCache']);

app.config(['$routeProvider', '$locationProvider', function($routeProvider, $locationProvider){
    $routeProvider
        .when('/addToDo',{
            templateUrl: 'templates/addToDo.html',
            controller: 'addToDoController'
        })
        .otherwise({redirectTo: '/'});

        $locationProvider.html5Mode({
            enabled: true,
            requireBase: false
        });
}]);

