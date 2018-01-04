'use strict';

angular.module('taskApp').controller('TaskController',
    ['TaskService', '$scope',  function( TaskService, $scope) {

        var self = this;
        self.task = {};
        self.tasks=[];

        self.submit = submit;
        self.getAllTasks = getAllTasks;
        self.createTask = createTask;
        self.updateTask = updateTask;
        self.removeTask = removeTask;
        self.editTask = editTask;
        self.reset = reset;

        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;

        self.onlyIntegers = /^\d+$/;
        self.onlyNumbers = /^\d+([,.]\d+)?$/;

        function submit() {
            console.log('Submitting');
            if (self.task.id === undefined || self.task.id === null) {
                console.log('Saving New Task', self.task);
                createTask(self.task);
            } else {
                updateTask(self.task, self.task.id);
                console.log('Task updated with id ', self.task.id);
            }
        }

        function createTask(task) {
            console.log('About to create task');
            TaskService.createTask(task)
                .then(
                    function (response) {
                        console.log('Task created successfully');
                        self.successMessage = 'Task created successfully';
                        self.errorMessage='';
                        self.done = true;
                        self.task={};
                        $scope.myForm.$setPristine();
                    },
                    function (errResponse) {
                        console.error('Error while creating Task');
                        self.errorMessage = 'Error while creating Task: ' + errResponse.data.errorMessage;
                        self.successMessage='';
                    }
                );
        }


        function updateTask(task, id){
            console.log('About to update task');
            TaskService.updateTask(task, id)
                .then(
                    function (response){
                        console.log('Task updated successfully');
                        self.successMessage='Task updated successfully';
                        self.errorMessage='';
                        self.done = true;
                        $scope.myForm.$setPristine();
                    },
                    function(errResponse){
                        console.error('Error while updating Task');
                        self.errorMessage='Error while updating Task '+errResponse.data;
                        self.successMessage='';
                    }
                );
        }


        function removeTask(id){
            console.log('About to remove Task with id '+id);
            TaskService.removeTask(id)
                .then(
                    function(){
                        console.log('Task '+id + ' removed successfully');
                    },
                    function(errResponse){
                        console.error('Error while removing task '+id +', Error :'+errResponse.data);
                    }
                );
        }


        function getAllTasks(){
            return TaskService.getAllTasks();
        }

        function editTask(id) {
            self.successMessage='';
            self.errorMessage='';
            TaskService.getTask(id).then(
                function (task) {
                    self.task = task;
                },
                function (errResponse) {
                    console.error('Error while removing task ' + id + ', Error :' + errResponse.data);
                }
            );
        }
        function reset(){
            self.successMessage='';
            self.errorMessage='';
            self.task={};
            $scope.myForm.$setPristine(); //reset Form
        }
    }


    ]);