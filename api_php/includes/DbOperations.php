<html>
    <head>
        <script>  
            function confirmaExcluir(id){
                if (confirm('Deseja realmente deletar este Pet?')) {
                    window.location.replace("../v1/excluirPet.php?id="+id);
                } else {
                   
                 }
            }
            
            function confirmaEditar(id){
                if (confirm('Deseja realmente editar este Pet?')) {
                    window.location.replace("../v1/editarPet.php?id="+id);
                } else {
                   
                 }
            }
            function confirmaMais(id){
                  window.location.replace("../v1/maisInfoPet.php?id="+id);
            }
            
        </script>
    </head>
    <body>
        
    </body>
</html>

<?php 


	class DbOperations{

		private $con; 

		function __construct(){

			require_once dirname(__FILE__).'/DbConnect.php';

			$db = new DbConnect();

			$this->con = $db->connect();
			

		}

		/*CRUD -> C -> CREATE */

		public function createUser($username, $pass, $email, $cpf, $nome, $sobrenome,$cidade,$bairro,$rua,$cep,$numero,$telefoneum,$telefonedois,$classe){
			if($this->isUserExist($username,$email)){
				return 0; 
			}else{
				$password = md5($pass);
				$stmt = $this->con->prepare("INSERT INTO `users` (`id`, `username`, `password`,`classe`) VALUES (NULL, ?, ?, ?);");
				$stmt->bind_param("sss",$username,$password,$classe);
				
				if($stmt->execute()){
				    
					$stmt = $this->con->prepare("SELECT `id` FROM `users` WHERE username = ? AND password = ?");
				    $stmt->bind_param("ss",$username,$password);
				    
				    if($stmt->execute()){
				        $id_users = $stmt->get_result()->fetch_assoc()['id'];
				        
				        $stmt = $this->con->prepare("INSERT INTO `profile`(`id`, `cpf`, `nome`, `sobrenome`, `users_id`) VALUES (null,?,?,?,?);");
				        $stmt->bind_param("ssss",$cpf,$nome,$sobrenome,$id_users);

				        if($stmt->execute()){
				            	$stmt = $this->con->prepare("SELECT `id` FROM `profile` WHERE users_id = ?");
				                $stmt->bind_param("s",$id_users);
				            
				           if($stmt->execute()){
				    
				                $id_profile = $stmt->get_result()->fetch_assoc()['id'];
				                
				                $stmt = $this->con->prepare("INSERT INTO `endereco`(`id`, `cidade`, `bairro`, `rua`, `cep`, `numero`, `profile_id`, `profile_users_id`) VALUES (null,?,?,?,?,?,?,?);");
				                $stmt->bind_param("sssssss",$cidade,$bairro,$rua,$cep,$numero,$id_profile,$id_users);
				                
    				            if($stmt->execute()){
    				                $stmt = $this->con->prepare("INSERT INTO `contato`(`id`, `email`, `telefoneUm`, `telefoneDois`, `profile_id`, `profile_users_id`) VALUES (null,?,?,?,?,?);");
				                    $stmt->bind_param("sssss",$email,$telefoneum,$telefonedois,$id_profile,$id_users);
				                    if($stmt->execute()){
				                        return 1;
				                    }
    				            }
				           }
				        }
				    }
				}else{
					return 2; 
				}
				
			}
		}

		public function userLogin($username, $pass){
			$password = md5($pass);
			$stmt = $this->con->prepare("SELECT id FROM users WHERE username = ? AND password = ?");
			$stmt->bind_param("ss",$username,$password);
			$stmt->execute();
			$stmt->store_result(); 
			return $stmt->num_rows > 0; 
		}
        
        public function getPetsByUser($username){
			$stmt = $this->con->prepare("select a.nome as petname, a.peso, a.raca, a.ano_nascimento from users u, profile p, endereco e, contato c, animal a where u.id = p.users_id and p.id = e.profile_id and p.id = c.profile_id and p.id = a.profile_id and u.username = ?");
			$stmt->bind_param("s",$username);
			$stmt->execute();
			$result = $stmt->get_result();
			$pet = array();
			if($result->num_rows > 0){
			    
    			while($row = $result->fetch_assoc()) {
        			    $row_pet['petname'] = $row['petname'];
        			    $row_pet['peso'] = $row['peso'];
        			    $row_pet['raca'] = $row['raca'];
        			    $row_pet['ano_nascimento'] = $row['ano_nascimento'];
                        array_push($pet,$row_pet);
    			}
			    return $pet;
			}
			return null;
		}
		
		
		
		
		
        public function UpdateUser($iduser ,$username,$email,$cpf, $nome, $sobrenome,$cidade,$bairro,$rua,$numero,$cep,$telefoneUm,$telefoneDois,$classe){
            
            $stmt = $this->con->prepare("UPDATE `users` SET `username`=?,`classe`=? WHERE id= ?");
			$stmt->bind_param("sss",$username,$classe,$iduser);
			
			if($stmt->execute()){
			    
                $stmt = $this->con->prepare("UPDATE `profile` SET `cpf`= ?,`nome`= ?,`sobrenome`= ? WHERE users_id = ?");
    			$stmt->bind_param("ssss",$cpf,$nome,$sobrenome,$iduser);
    			
    			if($stmt->execute()){
    			    
    			    $stmt = $this->con->prepare("UPDATE `endereco` SET `cidade`= ?,`bairro`= ?,`rua`= ? ,`cep`= ?,`numero`= ? WHERE profile_users_id = ?");
    			    $stmt->bind_param("ssssss",$cidade,$bairro,$rua,$cep,$numero,$iduser);
    			    
    			    if($stmt->execute()){
    			        $stmt = $this->con->prepare("UPDATE `contato` SET `email`= ?,`telefoneUm`= ?,`telefoneDois`= ? WHERE profile_users_id = ?");
    			        $stmt->bind_param("ssss",$email,$telefoneUm,$telefoneDois,$iduser);
    			         if($stmt->execute()){
    			             $stmt->store_result();
    			             return 1;
    			         }else{
    			             return 2;
    			         }
    			    }
    			}
			} 
		
        }
        
        
		public function getUserByUsername($username){
			//$stmt = $this->con->prepare("SELECT * FROM users WHERE username = ?");
			$stmt = $this->con->prepare("select u.id ,u.username, u.password, p.cpf,p.nome,p.sobrenome, e.cidade, e.bairro, e.rua, e.cep, e.numero, c.email, c.telefoneUm, c.telefoneDois, a.nome as petname, a.peso, a.raca, a.ano_nascimento from users u, profile p, endereco e, contato c, animal a where u.id = p.users_id and p.id = e.profile_id and p.id = c.profile_id and p.id = a.profile_id and u.username = ?");
			$stmt->bind_param("s",$username);
			$stmt->execute();
			return $stmt->get_result()->fetch_assoc();
		}
		
		
		
		public function getUserByID($id){
			//$stmt = $this->con->prepare("SELECT * FROM users WHERE username = ?");
			$stmt = $this->con->prepare("select u.id , u.classe ,u.username, u.password, p.cpf,p.nome,p.sobrenome, e.cidade, e.bairro, e.rua, e.cep, e.numero, c.email, c.telefoneUm, c.telefoneDois, a.nome as petname, a.peso, a.raca, a.ano_nascimento from users u, profile p, endereco e, contato c, animal a where u.id = p.users_id and p.id = e.profile_id and p.id = c.profile_id and p.id = a.profile_id and u.id = ?");
			$stmt->bind_param("s",$id);
			$stmt->execute();
			return $stmt->get_result()->fetch_assoc();
		}
		
		
		public function getAllUsers(){
			//$stmt = $this->con->prepare("SELECT * FROM users WHERE username = ?");
		    $stmt = $this->con->prepare("select u.id ,u.username, u.password, p.cpf,p.nome,p.sobrenome, e.cidade, e.bairro, e.rua, e.cep, e.numero, c.email, c.telefoneUm, c.telefoneDois from users u, profile p, endereco e, contato c where u.id = p.users_id and p.id = e.profile_id and p.id = c.profile_id");
		    $stmt->execute();
			return $stmt;
		}
		
		public function getUserPets($id){
			//$stmt = $this->con->prepare("SELECT * FROM users WHERE username = ?");
		    $stmt = $this->con->prepare("select a.id, a.nome as petname, a.peso, a.raca, a.ano_nascimento from users u, profile p, endereco e, contato c, animal a where u.id = p.users_id and p.id = e.profile_id and p.id = c.profile_id and p.id = a.profile_id and u.id = ?");
		    $stmt->bind_param("s",$id);
		    $stmt->execute();
			return $stmt;
		}
		
			public function getPet($id){
			//$stmt = $this->con->prepare("SELECT * FROM users WHERE username = ?");
		    $stmt = $this->con->prepare("select a.id, a.nome as petname, a.peso, a.raca, a.ano_nascimento from users u, profile p, endereco e, contato c, animal a where u.id = p.users_id and p.id = e.profile_id and p.id = c.profile_id and p.id = a.profile_id and a.id = ?");
		    $stmt->bind_param("s",$id);
		    $stmt->execute();
		    return $stmt->get_result()->fetch_assoc();
		}
		
		
		
		

		private function isUserExist($username, $email){
			$stmt = $this->con->prepare("SELECT u.id FROM users u JOIN contato c ON u.id = c.profile_id WHERE u.username = ? OR c.email = ?");
			$stmt->bind_param("ss", $username, $email);
			$stmt->execute(); 
			$stmt->store_result(); 
			return $stmt->num_rows > 0; 
		}
		
    	public function deleteUser($id){
    	    $stmt = $this->con->prepare("DELETE FROM `consultas` WHERE animal_profile_users_id = ?");
			$stmt->bind_param("s", $id);
			if($stmt->execute()){
			    $stmt = $this->con->prepare("DELETE FROM `animal` WHERE profile_users_id = ?");
			    $stmt->bind_param("s", $id);
			    if($stmt->execute()){
			        $stmt = $this->con->prepare("DELETE FROM `contato` WHERE profile_users_id = ?");
			        $stmt->bind_param("s", $id);
			        if($stmt->execute()){
			            $stmt = $this->con->prepare("DELETE FROM `endereco` WHERE profile_users_id = ?");
			            $stmt->bind_param("s", $id);
			            if($stmt->execute()){
			                $stmt = $this->con->prepare("DELETE FROM `profile` WHERE users_id = ?");
			                $stmt->bind_param("s", $id);
			                 if($stmt->execute()){
			                     $stmt = $this->con->prepare("DELETE FROM `users` WHERE id = ?");
			                     $stmt->bind_param("s", $id);
			                     if($stmt->execute()){
			                         	$stmt->store_result(); 
			                            return $stmt->num_rows > 0; 
			                     }
			                 }
			            }
			        }
			    }
			}
			return null;
    	}
		
		public function cadastrarPet($iduser, $nomepet, $nascimento, $peso,$raca){
		    $stmt = $this->con->prepare("SELECT `id` FROM `profile` WHERE users_id = ?");
			$stmt->bind_param("s",$iduser);
			
			if($stmt->execute()){
			    
			    $profid = $stmt->get_result()->fetch_assoc()['id'];
			    
			    $stmt = $this->con->prepare("INSERT INTO `animal`(`id`, `nome`, `peso`, `raca`, `ano_nascimento`, `profile_id`, `profile_users_id`) VALUES (null,?,?,?,?,?,?);");
			    $stmt->bind_param("ssssss",$nomepet,$peso,$raca,$nascimento,$profid,$iduser);
			    if($stmt->execute()){
			        return 1;
			    }else{
			        return 2;
			    }
			}
		    
		    
		}
		
		
		public function TablePets($id){
		    if($id != null){
                $db = new DbOperations(); 
		        $user = $db->getUserPets($id);
		        
		      if($user != null){
    	        $result = $user->get_result();
    			if($result->num_rows > 0){
    			    echo'<table id="customers">';
    			    echo'<tr>';
    			    echo'<th>ID</th>';
    			    echo'<th>Nome Pet</th>';
    			    echo'<th>Peso</th>';
    			    echo'<th>Raça</th>';
    			    echo'<th>Nascimento</th>';
    			    
    			    echo'<th>Ações</th>';
    			    echo'</tr>';
    			    echo'<tr>';
    			    echo'<br><br>';
        			while($row = $result->fetch_assoc()) {
        			   echo '<td>'.$row['id'].'</td>'; 
                       echo '<td>'.$row['petname'].'</td>';
                       echo '<td>'.$row['peso'].'</td>';
                       echo '<td>'.$row['raca'].'&nbsp'.'</td>';
                       echo '<td>'.$row['ano_nascimento'].'</td>'; 
                       echo '<td><a onClick="confirmaMais('.$row['id'].');"   href="#" >&nbspMais&nbsp</a>'.'&nbsp'; 
                       echo '<a onClick="confirmaEditar('.$row['id'].');"     href="#" >&nbspEditar&nbsp</a>'.'&nbsp';
                       echo '<a onClick="confirmaExcluir('.$row['id'].');"    href="#" >&nbspExcluir&nbsp</a>'.'</td>';
                       echo'</tr>';
        			}
        			
        		    echo'</table>';
        		    echo'<br><br>';
        		    echo'<br><br>';
        		    echo'<br><br>';
    			}else{
    		        echo'<h1>Não há Pets Registrados!</h1>';
    			}
        }
		        
		    }else{
		        echo'<h1> Falha ao recuperar pets </h1>';
		    }
		}
		
    	public function isEmailExist($email){
    		$stmt = $this->con->prepare("SELECT id FROM users WHERE email = ?");
    		$stmt->bind_param("s", $email);
    		$stmt->execute(); 
    		$stmt->store_result(); 
    		return $stmt->num_rows > 0; 
        }
        
        
        
       public function deletePet($id){
           $stmt = $this->con->prepare("DELETE FROM `consultas` WHERE animal_id = ?");
			$stmt->bind_param("s", $id);
			if($stmt->execute()){
			     $stmt = $this->con->prepare("DELETE FROM `animal` WHERE id = ?");
			     $stmt->bind_param("s", $id);
			    if($stmt->execute()){
			        return 1;
			    }else{
			        return 2;
			    }
			}
       }
       
       
       public function UpdatePet($idpet, $petname,$peso,$nascimento,$raca){
            $stmt = $this->con->prepare("UPDATE `animal` SET `nome`= ?,`peso`= ?,`raca`= ?,`ano_nascimento`= ? WHERE id = ?");
			$stmt->bind_param("sssss",$petname,$peso,$raca,$nascimento,$idpet);
			if($stmt->execute()){
			   	$stmt->store_result();
			   	return 1;
			}else{
			    return 2;
			}
       }

	}
	
	
	