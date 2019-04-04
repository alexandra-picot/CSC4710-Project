function showFieldSearch() {
    var divFieldSearch = document.getElementById("divFieldSearch");
    var divSpecialSearch = document.getElementById("divSpecialSearch");
    divFieldSearch.style.display = "block";
    divSpecialSearch.style.display = "none";
}

function showSpecialSearch() {
    var divFieldSearch = document.getElementById("divFieldSearch");
    var divSpecialSearch = document.getElementById("divSpecialSearch");
    divFieldSearch.style.display = "none";
    divSpecialSearch.style.display = "block";
}

function genericShowFieldSearch(divName, checkboxName) {
    var div = document.getElementById(divName);
    var checkbox = document.getElementById(checkboxName);

    if (checkbox.checked) {
        div.style.display = "block";
    } else {
        div.style.display = "none";
    }
}

function showPaperStatus() {
    genericShowFieldSearch("divPaperStatus", "paperStatus")
}

function showAuthorFields() {
    genericShowFieldSearch("divAuthorFields", "authorFields")
}

function showAuthorSpecial() {
    genericShowFieldSearch("divAuthorSpecial", "authorSpecial")
}

function showReviewerFields() {
    genericShowFieldSearch("divReviewerFields", "reviewerFields")
}

function showPaperFields() {
    genericShowFieldSearch("divPaperFields", "paperFields")
}

function showAuthorContributionSelect() {
    var select = document.getElementById("selectAuthorContribution");

    var checkbox = document.getElementById("authorContribution");

    if (checkbox.checked) {
        select.attributes.removeNamedItem("disabled");
    } else {
        select.disabled = true;
    }
}

function showCoAuthorInputs() {
    var divRejectedBy = document.getElementById("divRejectedBy");
    var divCoAuthor = document.getElementById("divCoAuthor");
    divRejectedBy.style.display = "none";
    divCoAuthor.style.display = "block";
}

function showRejectedByInputs() {
    var divRejectedBy = document.getElementById("divRejectedBy");
    var divCoAuthor = document.getElementById("divCoAuthor");
    divRejectedBy.style.display = "block";
    divCoAuthor.style.display = "none";
}

function showAdvancedSearch() {
    var x = document.getElementById("divAdvancedSearch");
    if (x.style.display === "none") {
        x.style.display = "block";
    } else {
        x.style.display = "none";
    }
}

function validateCoAuthor() {
    var firstSelect = document.getElementById("firstAuthor");
    var secondSelect = document.getElementById("secondAuthor");

    var firstAuthor = firstSelect.options[firstSelect.selectedIndex].value;
    var secondAuthor = secondSelect.options[secondSelect.selectedIndex].value;

    var submit = true;

    var coAuthorRdGroup = document.getElementById('authorTypeCoAuthor');
    if (!(coAuthorRdGroup.checked)) {
        return true;
    }

    if (firstAuthor.localeCompare(secondAuthor) == 0) {
        alert("Please, select two different co-authors.\nYou currently have the same author twice!");
        submit = false;
    }
    return submit;
}