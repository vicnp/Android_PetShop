
<?php 

require_once '../includes/DbOperations.php';

$response = array(); 

if($_SERVER['REQUEST_METHOD']=='POST'){
	if(isset($_POST['username']) and 
	        isset($_POST['email']) and
	             isset($_POST['password']) and
	                isset($_POST['cpf']) and 
	                    isset($_POST['nome']) and 
	                        isset($_POST['sobrenome'])and 
	                            isset($_POST['cidade'])and 
	                                isset($_POST['bairro'])and 
	                                    isset($_POST['rua'])and 
	                                        isset($_POST['numero'])and 
	                                            isset($_POST['cep'])and 
	                                                isset($_POST['telefoneUm'])and 
	                                                    isset($_POST['telefoneDois']))
		{
		//operate the data further 

		$db = new DbOperations(); 

		$result = $db->createUser( 	$_POST['username'],
									$_POST['password'],
									$_POST['email'],
									$_POST['cpf'],
									$_POST['nome'],
									$_POST['sobrenome'],
									$_POST['cidade'],
									$_POST['bairro'],
									$_POST['rua'],
									$_POST['cep'],
									$_POST['numero'],
									$_POST['telefoneUm'],
									$_POST['telefoneDois']
									
								);
		if($result == 1){
			$response['error'] = false; 
			$response['message'] = "Usuário Registrado com Sucesso";
		}elseif($result == 2){
			$response['error'] = true; 
			$response['message'] = "Ocorreu um erro!";			
		}elseif($result == 0){
			$response['error'] = true; 
			$response['message'] = "Este usuário ou email já estão cadastrados!";						
		}

	}else{
		$response['error'] = true; 
		$response['message'] = "Preencha todos os campos.";
	}
}else{
	$response['error'] = true; 
	$response['message'] = "Requisição Inválida.";
}

echo json_encode($response);
