async function login() {

    let account = document.getElementById("account").value;
    let pin = document.getElementById("pin").value;

    if (account === "" || pin === "") {
        alert("Please enter Account Number and PIN!");
        return;
    }

    try {

        const response = await fetch("http://localhost:8080/api/atm/login", {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify({
                accountNumber: account,
                pin: pin
            })

        });

        if (response.ok) {

            const customer = await response.json();

            sessionStorage.setItem("atmCustomer", JSON.stringify(customer));

            alert("Login Successful! Welcome " + customer.name);

            window.location.href = "dashboard.html";

        } else {

            alert("Invalid Account Number or PIN!");

        }

    } catch (error) {

        alert("Cannot connect to ATM server. Please try again.");

        console.log(error);

    }
}
