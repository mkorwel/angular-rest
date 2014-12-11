var myApp = angular.module('myApp',['ngRoute']);

myApp.config(function($routeProvider) {
	  $routeProvider
	    .when('/', {
	      controller:'UsersController',
	      templateUrl:'partials/list.html'
	    })
	    .when('/edit/:userId', {
	      controller:'UserEditController',
	      templateUrl:'partials/detail.html'
	    })
	    .when('/add', {
	      controller:'UserAddController',
	      templateUrl:'partials/detail.html'
	    })
	    .otherwise({
	      redirectTo:'/'
	    });
	});

myApp.controller("UsersController", function($scope, $http){
	$http.get("rest/users").success(function (data){
		$scope.users = data;
	});
	
	$scope.remove = function(id){
		$http.delete("rest/users/" + id).success(function (){
		});
	}
});

myApp.controller("UserEditController", function($scope, $http, $routeParams, $location){
	$http.get("rest/users/" + $routeParams.userId).success(function (data){
		$scope.user = data;
	});
	
	$scope.save = function(){
		$http.put("rest/users/" + $scope.user.id, $scope.user).success(function (){
			$location.path( "/" );
		});
	}
	
	$scope.activate = function(){
		$http.patch("rest/users/" + $scope.user.id + "/activate", $scope.user).success(function (){
			$location.path( "/" );
		});
	}
});

myApp.controller("UserAddController", function($scope, $http, $location){
	$scope.user = {};
	
	$scope.save = function(){
		$http.post("rest/users/", $scope.user).success(function (){
			$location.path( "/" );
		});
	}
});