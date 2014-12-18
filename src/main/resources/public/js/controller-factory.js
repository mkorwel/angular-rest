var factoryExample = angular.module('factoryExample',['ngRoute', 'ngResource']);

factoryExample.factory('Users', function($resource) {
	  return $resource('/rest/users/:id', { id: '@id' }, {
	    update: {
	      method: 'PUT'
	    }
	  });
	});

factoryExample.config(function($routeProvider) {
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

factoryExample.controller("UsersController", function($scope, $http, $location, Users){
	
	$scope.users = Users.query();
	$scope.filter = {};
	
	$scope.refresh = function(){
		$scope.users = Users.query({ filterName: $scope.filter.name, filterStatus: $scope.filter.status });
	};
	
	$scope.remove = function(user) {
	      user.$delete(function() {
	    	  $scope.refresh();
	      });
	  };
});

factoryExample.controller("UserEditController", function($scope, $http, $routeParams, $location, Users){
	$scope.user = Users.get({ id: $routeParams.userId }, function() {});
	
	$scope.save = function() {
		$scope.user.$update(function() {
			$location.path( "/" );
	      });
	};
	
	$scope.activate = function(){
		$http.patch("rest/users/" + $scope.user.id + "/activate", $scope.user).success(function (){
			$location.path( "/" );
		});
	}
});

factoryExample.controller("UserAddController", function($scope, $http, $location, Users){
	$scope.user = new Users();
	$scope.save = function() {
		$scope.user.$save(function() {
			$location.path( "/" );
	    });
	};
	
});