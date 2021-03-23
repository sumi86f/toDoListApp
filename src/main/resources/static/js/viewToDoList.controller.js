app.controller('viewToDoListController', ['$rootScope','$scope', '$http', '$uibModal', function($rootScope, $scope, $http, $uibModal) {

    $scope.populateViewModal = function (toDo) {
        $scope.toDo = {};
        $scope.toDo.id = toDo.id;
        $scope.toDo.name = toDo.name;
        $scope.toDo.description = toDo.description;
        $scope.toDo.dueDate = (toDo.dueDate == undefined || toDo.dueDate == "") ? "" : new Date(toDo.dueDate);
    };

    $scope.populateStatusOptionOnModal = function(toDo){
        var selectedOption = (toDo.status == "Pending") ? {id: '1', name: 'Pending'} : {id: '2', name: 'Done'};
        $scope.status = {
            options: [
            {id: '1', name: 'Pending'},
            {id: '2', name: 'Done'}
            ],
            selectedOption: selectedOption
        };
    };

    $scope.modalBoxValidation = function(viewMode, toDo){
        $scope.isCloseEnabled = true;
        $scope.isStatusDisabled = false;
        $scope.isDisabled = false;
        $scope.isStatusDisabled = (viewMode == 'edit');
        $scope.isDisabled = (viewMode == 'view' );
        $scope.isViewMode = (viewMode == 'view' && !toDo.status == 'Done');
        if(toDo.status == 'Done') {
            $scope.isStatusDisabled = true;
            $scope.isDisabled = true;
        }
    };

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


    $scope.updateToDo = function(toDo) {
        if(!toDo.selected) {
            toDo.status = 'Done';
            toDo.dueDate = (toDo.dueDate == undefined || toDo.dueDate == "") ? "" : moment(toDo.dueDate).format("YYYY-MM-DD");
            $http({
                method: 'PUT',
                url: 'todo/update',
                data: toDo,
                headers: { 'Content-Type': 'application/json; charset=utf-8' }
            }).then(function (response) {
                $scope.populateData();
            }, function (error) {
                $scope.errorMessage = error.data.details[0];
                $scope.toDo.selected = false; 
            });
        }
    };

    $scope.deleteToDo = function(toDo) {
        var index = $scope.allToDoList.indexOf(toDo);
        var id = toDo.id;
        $http({
            method: 'DELETE',
            url: 'todo/delete/' + id,
            headers: { 'Content-Type': 'application/json; charset=utf-8' }
        }).then(function(response) {
            console.log("Deleted record from the database");
            $scope.allToDoList.splice(index, 1);
        }, function(error) {
            $scope.errorMessage = error.data.details[0];
        });
    };

    $scope.openModal = function(toDo, viewMode) {

        $scope.populateViewModal(toDo);

        $uibModal.open({
            templateUrl:  'templates/addToDo.html',
            scope: $scope,
            size: 'lg',
            controller: function ($scope, $uibModalInstance) {
                $scope.populateStatusOptionOnModal(toDo);
                $scope.modalBoxValidation(viewMode, toDo);
                $scope.addOrUpdateToDo = function (toDo) {
                    toDo.status = $scope.status.selectedOption.name;
                    toDo.dueDate = (toDo.dueDate == undefined || toDo.dueDate == "") ? "" : moment(toDo.dueDate).format("YYYY-MM-DD");
                    $http({
                        method: 'PUT',
                        url: 'todo/update',
                        data: toDo,
                        headers: { 'Content-Type': 'application/json; charset=utf-8' }
                    }).then(function (response) {
                        $scope.populateData();
                        $scope.cancel(); 
                    }, function (error) {
                        $scope.errorMessage = error.data.details[0];
                    });
                };

                $scope.cancel = function () {
                    $uibModalInstance.dismiss('cancel'); 
                };
            },
        });
}
}]);
