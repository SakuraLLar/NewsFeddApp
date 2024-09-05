package sakura.llar.news.data

interface RequestResponseMergeStrategy<E> {
    fun merge(right: RequestResult<E>, left: RequestResult<E>): RequestResult<E>
}