let transactions = [];

function getCustomer() {
    let data = sessionStorage.getItem("atmCustomer");

    if (!data) {
        alert("Please login first!");
        window.location.href = "index.html";
        return null;
    }

    return JSON.parse(data);
}

async function checkBalance() {
    let customer = getCustomer();
    if (!customer) return;

    try {
        let response = await fetch(
            `http://localhost:8080/api/atm/balance/${customer.accountNumber}`
        );

        if (response.ok) {
            let balance = await response.json();
            alert("Your Available Balance is: Rs. " + balance);
        } else {
            alert("Unable to fetch balance.");
        }
    } catch (error) {
        alert("Cannot connect to ATM server.");
    }
}

async function deposit() {
    let customer = getCustomer();
    if (!customer) return;

    let amount = Number(prompt("Enter Deposit Amount:"));

    if (!Number.isFinite(amount) || amount <= 0) {
        alert("Please enter a valid amount.");
        return;
    }

    try {
        let response = await fetch(
            `http://localhost:8080/api/atm/deposit/${customer.accountNumber}/${amount}`,
            { method: "POST" }
        );

        let message = await response.text();
        alert(message);

        if (response.ok) {
            transactions.push("Deposited: Rs. " + amount);
        }
    } catch (error) {
        alert("Cannot connect to ATM server.");
    }
}

async function withdraw() {
    let customer = getCustomer();
    if (!customer) return;

    let amount = Number(prompt("Enter Withdrawal Amount:"));

    if (!Number.isFinite(amount) || amount <= 0) {
        alert("Please enter a valid amount.");
        return;
    }

    try {
        let response = await fetch(
            `http://localhost:8080/api/atm/withdraw/${customer.accountNumber}/${amount}`,
            { method: "POST" }
        );

        let message = await response.text();
        alert(message);

        if (response.ok) {
            transactions.push("Withdrawn: Rs. " + amount);
        }
    } catch (error) {
        alert("Cannot connect to ATM server.");
    }
}

function miniStatement() {
    if (transactions.length === 0) {
        alert("No Transactions Available");
    } else {
        alert(transactions.join("\n"));
    }
}

function logout() {
    sessionStorage.removeItem("atmCustomer");
    alert("Thank you for using our ATM!");
    window.location.href = "index.html";
}