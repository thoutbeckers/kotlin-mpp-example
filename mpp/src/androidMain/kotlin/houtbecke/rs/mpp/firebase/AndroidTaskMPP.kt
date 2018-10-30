package houtbecke.rs.mpp.firebase

import com.google.android.gms.tasks.Task

class UnitAndroidTaskMPP(override val base: Task<Void>): AndroidTaskMPP<Void, Unit>(base) {
    override fun result(result: Void?): Unit? {
        return Unit
    }
    override fun task(task: Task<Void>): TaskMPP<Unit> {
        return UnitAndroidTaskMPP(task)
    }
}


abstract class AndroidTaskMPP<TResult, TResultMPP>(open val base: Task<TResult>): TaskMPP<TResultMPP> {

    abstract fun result(result: TResult?): TResultMPP?
    abstract fun task(task: Task<TResult>): TaskMPP<TResultMPP>

    override fun isComplete(): Boolean {
        return base.isComplete
    }

    override fun isSuccessful(): Boolean {
        return base.isSuccessful
    }

    override fun isCanceled(): Boolean {
        return base.isCanceled
    }

    override fun result(): TResultMPP? {
        return result(base.result)
    }

    override fun exception(): Exception {
        return Exception(base.exception)
    }

    override fun onSuccess(listener: OnSuccess<TResultMPP>): TaskMPP<TResultMPP> {
        base.addOnSuccessListener{
            listener.invoke(result(it)!!)
        }
        return this
    }

    override fun onFailure(listener: OnFailure): TaskMPP<TResultMPP> {
        base.addOnFailureListener{
            listener.invoke(it)
        }
        return this
    }

    override fun onComplete(listener: OnComplete<TResultMPP>): TaskMPP<TResultMPP> {
        base.addOnCompleteListener {
            listener.invoke(task(it))
        }
        return this
    }

    override fun onCanceled(listener: OnCanceled): TaskMPP<TResultMPP> {
        base.addOnCanceledListener {
            listener.invoke()
        }
        return this
    }
}