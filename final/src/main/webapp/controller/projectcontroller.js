/**
 * 
 */


var app  =  angular.module("app",[]);

app.controller("ViewStudentsController",function($scope){
	
	$scope.first="this is demo app";
	//var users = $resource("http://www.learn-angular.org/ResourceLesson/Users/:id");
	var users = $resource("http://localhost:8080/springang/getAllEmployee");
	$scope.viewEmployee=users.query();
})