package houtbecke.rs.mpp.firebase

typealias OnFailure = (Exception) -> Unit
typealias OnSuccess<TResultMPP> = (TResultMPP) -> Unit
typealias OnComplete<TResultMPP> = (TaskMPP<TResultMPP>) -> Unit
typealias OnCanceled = () -> Unit

typealias UnitTaskMPP = TaskMPP<Unit>

interface TaskMPP<TResult> {
    fun isComplete(): Boolean

    fun isSuccessful(): Boolean

    fun isCanceled(): Boolean

    fun result(): TResult?

    fun exception(): Exception

    fun onSuccess(listener: OnSuccess<TResult> ): TaskMPP<TResult>
    fun onFailure(listener: OnFailure): TaskMPP<TResult>
    fun onComplete(listener: OnComplete<TResult>): TaskMPP<TResult>
    fun onCanceled(listener: OnCanceled): TaskMPP<TResult>

// Continuations not supported yet.
//    fun <TContinuationResult> continueWith( var1: Continuation): TaskMPP<TContinuationResult>
//
//    fun <TContinuationResult> continueWithTask( var1: Continuation): TaskMPP<TContinuationResult>
//
//    fun <TContinuationResult> onSuccessTask( var1: SuccessContinuation): TaskMPP<TContinuationResult>
//
}
