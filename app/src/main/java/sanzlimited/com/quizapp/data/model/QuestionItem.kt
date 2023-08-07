package sanzlimited.com.quizapp.data.model

data class QuestionItem(
    val answers: Answers,
    val category: String,
    val correct_answer: String,
    val correct_answers: CorrectAnswers,
    val description: String,
    val difficulty: String,
    val explanation: Any,
    val id: Int,
    val multiple_correct_answers: String,
    val question: String,
    val tags: List<Tag>,
    val tip: Any
)