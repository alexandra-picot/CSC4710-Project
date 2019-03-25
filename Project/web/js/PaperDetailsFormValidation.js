function validateForm() {
    var firstReviewer = document.forms["assignReviewers"]["firstReviewer"].value;
    var secondReviewer = document.forms["assignReviewers"]["secondReviewer"].value;
    var thirdReviewer = document.forms["assignReviewers"]["thirdReviewer"].value;

    var submit = true;

    /**
     * Check if there is a value assigned to the first reviewer
     */
    if (!firstReviewer) {
        document.getElementById('firstReviewerError').innerText="Please, select a reviewer!";
        submit = false;
    } else {
        document.getElementById('firstReviewerError').innerText="";
    }

    /**
     * Check if there is a value assigned to the second reviewer
     */
    if (!secondReviewer) {
        document.getElementById('secondReviewerError').innerHTML="Please, select a reviewer!";
        submit = false;
    } else {
        document.getElementById('secondReviewerError').innerText="";
    }

    /**
     * Check if there is a value assigned to the third reviewer
     */
    if (!thirdReviewer) {
        document.getElementById('thirdReviewerError').innerHTML = "Please, select a reviewer!";
        submit = false;
    } else {
        document.getElementById('thirdReviewerError').innerText="";
    }

    /**
     * Check if one reviewer is assigned two times or more
     */
    if ((firstReviewer && secondReviewer && firstReviewer.localeCompare(secondReviewer) == 0) ||
        (firstReviewer && thirdReviewer && firstReviewer.localeCompare(thirdReviewer) == 0) ||
        (thirdReviewer && secondReviewer && thirdReviewer.localeCompare(secondReviewer) == 0)
    ) {
        alert("Please, select three different reviewers.\nYou currently have one reviewer assigned two or more times!");
        submit = false;
    }
    return submit;
}