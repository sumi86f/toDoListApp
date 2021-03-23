app.controller('viewToDoListController', ['$scope', '$http', '$uibModal', function($scope, $http, $uibModal) {

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
    }

    $scope.openModal = function(toDo, viewMode) {
        $scope.toDo = this.toDo;

        $uibModal.open({
            templateUrl:  'templates/addToDo.html',
            scope: $scope,
            size: 'lg',
            controller: function ($scope, $uibModalInstance) {
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
                $scope.modalBoxValidation = function(viewMode){

                 $scope.isStatusDisabled = false;
                 $scope.isDisabled = false;
                 $scope.isStatusDisabled = (viewMode == 'edit');
                 $scope.isDisabled = (viewMode == 'view' );
                 if(toDo.status == 'Done') {
                    $scope.isStatusDisabled = true;
                    $scope.isDisabled = true;
                }
                };
               $scope.populateStatusOptionOnModal(toDo);
               $scope.modalBoxValidation(viewMode);
               
            $scope.addOrUpdateToDo = function (toDo) {
                toDo.status = $scope.status.selectedOption.name;
                $http({
                    method: 'PUT',
                    url: 'todo/update',
                    data: toDo,
                    headers: { 'Content-Type': 'application/json; charset=utf-8' }
                }).then(function (response) {
                    $scope.cancel(); 
                }, function (error) {
                    $scope.errorMessage = error.data.details[0];
                });
            }
            $scope.cancel = function () {
                $uibModalInstance.dismiss('cancel'); 
            };
        
    },
});
}
}]);
