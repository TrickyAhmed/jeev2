<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="model.Pizza"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Order Pizza</title>
<%@ include file="/include/css.jsp"%>
</head>
<body>
	<%@ include file="/include/header.jsp"%>

	<div class="container">
	<%
        // Retrieve the pizza details from the request attribute set by the servlet
        Pizza pizza = (Pizza) request.getAttribute("pizza");
        System.out.println("Debug: Retrieved pizza from request attribute: " + pizza);
		
        if (pizza != null) {
    %>
		<h2>Order Pizza</h2>
		<form action="AddToCartServlet" method="post">
			<div class="form-group">
				<label for="pizzaName">Pizza Name: <%= pizza.getName() %></label>
				<input type="text" class="form-control" id="pizzaName" name="pizzaName" value="<%= pizza.getName() %>" readonly>
			</div>
			<div class="form-group">
				<label for="size">Size:</label>
				<select class="form-control" id="size" name="size" onchange="updateTotalPrice()">
					<option value="Small">Small ($8.00)</option>
					<option value="Medium">Medium ($10.00)</option>
					<option value="Large">Large ($12.00)</option>
				</select>
			</div>
			<div class="form-group">
				<label for="description">Description: <%= pizza.getDescription() %></label>
				<input type="text" class="form-control" id="description" name="description" value="<%= pizza.getDescription() %>" readonly>
			</div>
			<div class="form-group">
				<label for="price">Price: $<%= pizza.getPrice() %></label>
				<input type="text" class="form-control" id="price" name="price" value="<%= pizza.getPrice() %>" readonly>
			</div>
			<div class="form-group">
				<label for="quantity">Quantity:</label>
				<input type="number" class="form-control" id="quantity" name="quantity" value="1" min="1" onchange="updateTotalPrice()">
			</div>
			<button type="submit" class="btn btn-success">Add to Cart</button>
		</form>
		<%
        } else {
            System.out.println("Debug: No pizza details available in request attribute.");
    %>
		<p>No pizza details available.</p>
		<%
        }
    %>
    
	</div>

	<%@ include file="/include/footer.jsp"%>
	<%@ include file="/include/js.jsp"%>
	<script>
		// Extract pizza name from URL and set it to the input field
		const urlParams = new URLSearchParams(window.location.search);
		const pizzaName = urlParams.get('pizzaName');
		if (pizzaName) {
			console.log("Debug: Setting pizzaName from URL parameter: " + pizzaName);
			document.getElementById('pizzaName').value = pizzaName;
		}

		// Function to update total price based on quantity
		function updateTotalPrice() {
			const quantity = parseInt(document.getElementById('quantity').value);
			const size = document.getElementById('size').value;
			let price;

			// Set the price based on the selected size
			if (size === 'Small') {
				price = 8;
			} else if (size === 'Medium') {
				price = 10;
			} else if (size === 'Large') {
				price = 12;
			}

			// Calculate the total price
			const totalPrice = price * quantity;
			console.log("Debug: Calculated total price: " + totalPrice);

			// Update the price field
			document.getElementById('price').value = totalPrice.toFixed(2);
		}
	</script>
</body>
</html>
