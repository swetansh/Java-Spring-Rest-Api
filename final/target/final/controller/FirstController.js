

var app = angular.module("mainApp",['ngResource']);

app.controller('FirstController',
	function($scope,$resource){
		$scope.ran="vvvvvvvv";
		$scope.firstname="vv";
		//var users = $resource("http://www.learn-angular.org/ResourceLesson/Users/:id");
		var users = $resource("http://localhost:8080/final/project");
		$scope.result=users.get();
		
		$scope.getProject = function()
		{
			var users = $resource("http://localhost:8080/final/project");
			$scope.result=users;
			return users;
		}

	})