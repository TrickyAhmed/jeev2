<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chef Form</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #f6d365 0%, #fda085 100%);
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            background: #fff;
            padding: 3rem;
            border-radius: 20px;
            box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
            text-align: center;
            width: 400px;
        }
        h2 {
            color: #333;
            margin-bottom: 2rem;
            font-size: 28px;
            font-weight: bold;
        }
        form {
            margin-top: 2rem;
        }
        .input-container {
            position: relative;
            margin-bottom: 20px;
        }
        input[type='text'],
        input[type='password'],
        select {
            width: calc(100% - 40px);
            padding: 15px;
            border: none;
            border-radius: 30px;
            background-color: #f9f9f9;
            font-size: 16px;
            transition: background-color 0.3s;
        }
        input[type='text']:focus,
        input[type='password']:focus,
        select:focus {
            background-color: #e0e0e0;
            outline: none;
        }
        input[type='text']:not(:placeholder-shown),
        input[type='password']:not(:placeholder-shown),
        select:not(:placeholder-shown) {
            background-color: #e0e0e0;
        }
        input[type='submit'] {
            background: linear-gradient(135deg, #72edf2 0%, #5151e5 100%);
            color: white;
            padding: 15px 30px;
            border: none;
            border-radius: 30px;
            cursor: pointer;
            font-weight: bold;
            font-size: 16px;
            transition: background 0.3s ease;
        }
        input[type='submit']:hover {
            background: linear-gradient(135deg, #5151e5 0%, #72edf2 100%);
        }
        a {
            display: inline-block;
            margin-top: 1rem;
            color: #007bff;
            text-decoration: none;
            font-weight: bold;
            transition: color 0.3s ease;
        }
        a:hover {
            color: #0056b3;
        }
    </style>
    </style>
    <script>
        /* Fonction JavaScript pour afficher une vignette des images sélectionnées */
        function readFilesAndDisplayPreview(files) {
            let imageList = document.querySelector('#list'); 
            imageList.innerHTML = "";
            
            for ( let file of files ) {
                let reader = new FileReader();
                
                reader.addEventListener( "load", function( event ) {
                    let span = document.createElement('span');
                    span.innerHTML = '<img height="60" src="' + event.target.result + '" />';
                    imageList.appendChild( span );
                });

                reader.readAsDataURL( file );
            }
        }
    </script>
</head>
<body>
    <div class="container">
        <h2>${chef == null ? "Add New Chef" : "Edit Chef"}</h2>
        <form action="AdminChefController?action=${chef == null ? 'insert' : 'update'}" method="post" enctype="multipart/form-data">
            <div class="input-container">
                <input type='hidden' name='id' value="${chef == null ? '' : chef}"/>
                <input type='text' name='name' placeholder='Name' value="${chef == null ? '' : chef}" required>
            </div>
            <div class="input-container">
                <input type='text' name='description' placeholder='Description' value="${chef == null ? '' : chef.description}" required>
            </div>
         
            <div class="input-container">
                <input type='file' name='imageFile' accept='image/*' onchange="readFilesAndDisplayPreview(this.files);" required>
            </div>
            <div id="list"></div> <!-- Div pour prévisualisation des images -->
            <div>
                <input type='submit' value="${chef == null ? 'Save' : 'Update'}"/>
            </div>
        </form>
    </div>
</body>
</html>
