var app = angular.module('app',['ngResource']);

app.controller('displayController',function($scope,$resource){
	
	$scope.msg = "displaying details";
})