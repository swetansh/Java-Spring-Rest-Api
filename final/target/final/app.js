

var app = angular.module("app",['ngRoute','ngResource'])

app.config(['$routeProvider',function($routeProvider){
	
	$routeProvider.when("/add",{
		templateUrl:'view/add.html',
		controller:'addController'
	})
	.when("/display",{
		templateUrl:'view/display.html',
		controller:'displayController'
	})
}])