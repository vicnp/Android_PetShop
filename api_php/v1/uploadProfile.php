<?php 

	
    require_once 'dbDetails.php';

	$upload_path = 'v1/uploads/';

	$server_ip = gethostbyname(gethostname());

	$upload_url = 'https://vicnp69.000webhostapp.com/'.$upload_path;

	$response = array(); 

	if($_SERVER['REQUEST_METHOD'] == 'POST'){
		if(isset($_POST['name']) and isset($_FILES['image']['name']) and isset($_POST['email'])){
			$con = mysqli_connect(DB_HOST, DB_USERNAME, DB_PASSWORD, DB_NAME) or die('unable to connect to database ');
			$name = $_POST['name'];
            $email = $_POST['email'];
			$fileinfo = pathinfo($_FILES['image']['name']);

			$extension = $fileinfo['extension'];

			$file_url = $upload_url . getFileName() . '.'.$extension; 

			$file_path = $upload_path. getFileName(). '.'.$extension; 

			try{

				move_uploaded_file($_FILES['image']['tmp_name'], $file_path);
                //UPDATE mytable SET column1 = value1, column2 = value2 WHERE key_value = some_value;
				//$sql = "INSERT INTO users (url, name) VALUES ('$file_url','$name')";
				$sql = "UPDATE users SET url = '$file_url', name = '$name' WHERE email = '$email'";
				if(mysqli_query($con,$sql)){
					$response['error'] = false; 
					$response['url'] = $file_url; 
					$response['name'] = $name; 
				}

			}catch(Exception $e){
				$response['error'] = false; 
				$response['message'] = $e->getMessage(); 
			}
			mysqli_close($con);

		}else{
			$response['error'] = true; 
			$response['message'] = 'please choose a file';
		}
		
		echo json_encode($response);
	}

	function getFileName(){
		$con = mysqli_connect(DB_HOST, DB_USERNAME, DB_PASSWORD, DB_NAME) or die('Unable to connect');
		$sql = "SELECT max(id) as id FROM users";
		$result = mysqli_fetch_array(mysqli_query($con,$sql));
		mysqli_close($con);
		if($result['id']==null){
			return 1; 
		}else{
			return ++$result['id'];
		}
	}