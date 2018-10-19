
<?php 

require_once '../includes/DbOperations.php';

$response = array(); 

if($_SERVER['REQUEST_METHOD']=='POST'){
	if(
		isset($_POST['username']) and 
			isset($_POST['email']) and 
				isset($_POST['password']))
		{
		//operate the data further 

		$db = new DbOperations(); 

		$result = $db->createUser( 	$_POST['username'],
									$_POST['password'],
									$_POST['email']
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
