app.controller('addToDoController', ['$rootScope','$scope', '$http', function($rootScope, $scope, $http) {
    $scope.errorMessage = "";
    $scope.toDo = {};   
    $scope.isStatusDisabled = true;
    $scope.isDisabled = false;

    $scope.populateData = function () {
        $http({
            method : "GET",
            url : "todo/all"
        }).then(function (response) {
            $rootScope.allToDoList = response.data;
            $scope.errorMessage = "";
        }, function(error) {
            $scope.errorMessage = error.data.details[0];
        });    
    };

    $scope.populateStatusOptions = function () {
        $scope.status = {
            options: [
            {id: '1', name: 'Pending'},
            {id: '2', name: 'Done'}
            ],
            selectedOption: {id: '1', name: 'Pending'}
        };
    };

    $scope.populateData(); // populate data on load of page

    $scope.populateStatusOptions(); // to populate status options 

    $scope.addOrUpdateToDo = function(toDo) {
        var toDoObject = {
            name: $scope.toDo.name == undefined ? "" : $scope.toDo.name,
            description: $scope.toDo.description == undefined ? "" : $scope.toDo.description,
            dueDate: $scope.toDo.dueDate == undefined ? "" : moment($scope.toDo.dueDate).format("YYYY-MM-DD"),
            status: $scope.status.selectedOption.name
        };
        $http({
            method: 'POST',
            url: 'todo/add',
            data: toDoObject,
            headers: { 'Content-Type': 'application/json; charset=utf-8' }
        }).then(function(response) {
            $scope.toDo =  {};
            $scope.toDoListForm.$setPristine();
            $scope.errorMessage = "";
            $scope.populateData();
        }, function (error) {
            $scope.errorMessage = error.data.details[0];
        });
};   
}])